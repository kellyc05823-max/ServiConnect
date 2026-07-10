package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Service

data class ServiceRequestUiState(
    val service: Service? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
