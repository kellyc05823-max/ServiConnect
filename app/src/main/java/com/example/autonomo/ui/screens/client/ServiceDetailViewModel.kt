package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceDetailUiState())
    val uiState: StateFlow<ServiceDetailUiState> = _uiState.asStateFlow()

    fun loadService(id: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeService(id)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { service ->
                    _uiState.update { it.copy(service = service, isLoading = false) }
                }
        }
    }
}
