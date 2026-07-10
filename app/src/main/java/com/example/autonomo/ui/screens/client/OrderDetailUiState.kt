package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Request
import com.example.autonomo.domain.model.Service

data class OrderDetailUiState(
    val order: Request? = null,
    val service: Service? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
