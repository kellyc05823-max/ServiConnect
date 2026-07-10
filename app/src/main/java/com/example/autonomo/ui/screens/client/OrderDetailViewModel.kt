package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.RequestRepository
import com.example.autonomo.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val requestRepository: RequestRepository,
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailUiState())
    val uiState: StateFlow<OrderDetailUiState> = _uiState.asStateFlow()

    fun loadOrder(orderId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            requestRepository.observeRequest(orderId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { order ->
                    if (order != null) {
                        serviceRepository.observeService(order.serviceId).collect { service ->
                            _uiState.update { it.copy(order = order, service = service, isLoading = false) }
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = false, error = "Orden no encontrada") }
                    }
                }
        }
    }
}
