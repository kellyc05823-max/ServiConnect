package com.example.autonomo.ui.screens.auth

import com.example.autonomo.domain.model.User

data class AuthUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
