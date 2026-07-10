package com.example.autonomo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.autonomo.domain.model.Chat
import com.example.autonomo.domain.model.ChatParticipant
import com.example.autonomo.domain.model.Message

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val participantIds: List<String>,
    val participants: Map<String, ChatParticipant>,
    val lastMessage: Message?,
    val serviceRelatedId: String?,
    val serviceName: String?,
    val createdAt: Long,
    val updatedAt: Long
)

fun ChatEntity.toDomain(): Chat = Chat(
    id = id,
    participantIds = participantIds,
    participants = participants,
    lastMessage = lastMessage,
    serviceRelatedId = serviceRelatedId,
    serviceName = serviceName,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Chat.toEntity(): ChatEntity = ChatEntity(
    id = id,
    participantIds = participantIds,
    participants = participants,
    lastMessage = lastMessage,
    serviceRelatedId = serviceRelatedId,
    serviceName = serviceName,
    createdAt = createdAt,
    updatedAt = updatedAt
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val imageUrl: String?,
    val fileUrl: String?,
    val emoji: String?,
    val timestamp: Long,
    val isRead: Boolean,
    val isDeleted: Boolean
)

fun MessageEntity.toDomain(): Message = Message(
    id = id,
    senderId = senderId,
    text = text,
    imageUrl = imageUrl,
    fileUrl = fileUrl,
    emoji = emoji,
    timestamp = timestamp,
    isRead = isRead,
    isDeleted = isDeleted
)

fun Message.toEntity(chatId: String): MessageEntity = MessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    text = text,
    imageUrl = imageUrl,
    fileUrl = fileUrl,
    emoji = emoji,
    timestamp = timestamp,
    isRead = isRead,
    isDeleted = isDeleted
)
