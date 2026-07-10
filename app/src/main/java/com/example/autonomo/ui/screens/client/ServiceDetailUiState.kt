package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Service

data class ServiceDetailUiState(
    val service: Service? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
