package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Request

data class ClientOrdersUiState(
    val orders: List<Request> = emptyList(),
    val isLoading: Boolean = false,
    val selectedFilter: String = "Todas",
    val error: String? = null
)
