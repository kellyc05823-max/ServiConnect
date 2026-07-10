package com.example.autonomo.data.repository

import com.example.autonomo.domain.model.Chat
import com.example.autonomo.domain.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRepository {

    override fun observeUserChats(userId: String): Flow<List<Chat>> = callbackFlow {
        val listener = firestore.collection("chats")
            .whereArrayContains("participantIds", userId)
            .orderBy("updatedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val chats = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Chat::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(chats)
            }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override fun observeMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val listener = firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Message::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(messages)
            }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun sendMessage(chatId: String, message: Message): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val messageMap = mapOf(
                "senderId" to message.senderId,
                "text" to message.text,
                "imageUrl" to message.imageUrl,
                "fileUrl" to message.fileUrl,
                "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp(),
                "isRead" to false,
                "isDeleted" to false
            )
            
            firestore.collection("chats").document(chatId)
                .collection("messages").add(messageMap).await()
            
            // Update last message in chat document
            firestore.collection("chats").document(chatId)
                .update(
                    "lastMessage", message,
                    "updatedAt", System.currentTimeMillis()
                ).await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createChat(chat: Chat): Result<Chat> = withContext(Dispatchers.IO) {
        try {
            val docRef = firestore.collection("chats").add(chat).await()
            Result.success(chat.copy(id = docRef.id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTypingStatus(chatId: String, userId: String, isTyping: Boolean) {
        firestore.collection("chats").document(chatId)
            .update("participants.$userId.isTyping", isTyping)
    }

    override suspend fun markAsRead(chatId: String, userId: String) {
        // Implementation for marking messages as read
    }
}
