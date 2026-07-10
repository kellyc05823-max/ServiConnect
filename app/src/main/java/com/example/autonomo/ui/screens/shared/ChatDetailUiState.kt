package com.example.autonomo.ui.screens.shared

import com.example.autonomo.domain.model.Message

data class ChatDetailUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUserId: String? = null
)
