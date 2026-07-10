package com.example.autonomo.ui.screens.provider

import com.example.autonomo.domain.model.Request
import com.example.autonomo.domain.model.User

data class ProviderDashboardUiState(
    val provider: User? = null,
    val requests: List<Request> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
