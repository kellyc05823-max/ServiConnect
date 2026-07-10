package com.example.autonomo.ui.screens.provider

import com.example.autonomo.domain.model.Service

data class ProviderServicesUiState(
    val myServices: List<Service> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
