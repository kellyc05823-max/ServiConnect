package com.example.autonomo.domain.model

data class Chat(
    val id: String = "",
    val participantIds: List<String> = emptyList(),
    val participants: Map<String, ChatParticipant> = emptyMap(),
    val lastMessage: Message? = null,
    val serviceRelatedId: String? = null,
    val serviceName: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class ChatParticipant(
    val name: String = "",
    val photoUrl: String? = null,
    val isTyping: Boolean = false,
    val lastReadTimestamp: Long = 0L
)

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val imageUrl: String? = null,
    val fileUrl: String? = null,
    val emoji: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val isDeleted: Boolean = false
)
