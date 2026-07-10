package com.example.autonomo.data.repository

import com.example.autonomo.domain.model.Chat
import com.example.autonomo.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeUserChats(userId: String): Flow<List<Chat>>
    fun observeMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, message: Message): Result<Unit>
    suspend fun createChat(chat: Chat): Result<Chat>
    suspend fun updateTypingStatus(chatId: String, userId: String, isTyping: Boolean)
    suspend fun markAsRead(chatId: String, userId: String)
}
