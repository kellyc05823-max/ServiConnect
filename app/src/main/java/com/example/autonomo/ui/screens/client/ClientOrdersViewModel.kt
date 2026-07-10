package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.RequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientOrdersViewModel @Inject constructor(
    private val repository: RequestRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientOrdersUiState())
    val uiState: StateFlow<ClientOrdersUiState> = _uiState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {
        val userId = authRepository.currentUserId ?: return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeClientRequests(userId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { orders ->
                    _uiState.update { it.copy(orders = orders, isLoading = false) }
                }
        }
    }

    fun onFilterChanged(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }
}
