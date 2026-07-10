package com.example.autonomo.ui.screens.shared

import com.example.autonomo.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
