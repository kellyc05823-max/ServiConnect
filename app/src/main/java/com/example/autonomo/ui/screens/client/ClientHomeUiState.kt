package com.example.autonomo.ui.screens.client

import com.example.autonomo.domain.model.Service

data class ClientHomeUiState(
    val services: List<Service> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val error: String? = null
)
