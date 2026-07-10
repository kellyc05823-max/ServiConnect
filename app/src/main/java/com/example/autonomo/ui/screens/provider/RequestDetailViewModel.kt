package com.example.autonomo.ui.screens.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.data.repository.RequestRepository
import com.example.autonomo.ui.screens.client.OrderDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailViewModel @Inject constructor(
    private val repository: RequestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailUiState())
    val uiState: StateFlow<OrderDetailUiState> = _uiState.asStateFlow()

    fun loadRequest(requestId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeRequest(requestId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { request ->
                    _uiState.update { it.copy(order = request, isLoading = false) }
                }
        }
    }

    fun updateStatus(requestId: String, status: OrderStatus, reason: String? = null) {
        viewModelScope.launch {
            repository.updateStatus(requestId, status, reason)
        }
    }
}
