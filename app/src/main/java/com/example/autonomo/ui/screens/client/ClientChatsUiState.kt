package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Chat

data class ClientChatsUiState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
