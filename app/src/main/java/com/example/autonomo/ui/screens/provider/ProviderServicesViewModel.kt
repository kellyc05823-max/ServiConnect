package com.example.autonomo.ui.screens.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderServicesViewModel @Inject constructor(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderServicesUiState())
    val uiState: StateFlow<ProviderServicesUiState> = _uiState.asStateFlow()

    init {
        observeMyServices()
    }

    private fun observeMyServices() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // For mock, filtering by a specific provider name
            repository.observeServices()
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { services ->
                    val filtered = services.filter { it.providerName == "Carlos Mendoza" }
                    _uiState.update { it.copy(myServices = filtered, isLoading = false) }
                }
        }
    }
}
