package com.example.autonomo.ui.screens.provider

import com.example.autonomo.data.OrderStatus
import com.example.autonomo.domain.model.Request

data class RequestManagementUiState(
    val requests: List<Request> = emptyList(),
    val isLoading: Boolean = false,
    val selectedFilter: OrderStatus? = null,
    val error: String? = null
)
