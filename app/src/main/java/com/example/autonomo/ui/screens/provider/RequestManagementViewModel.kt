package com.example.autonomo.ui.screens.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.data.repository.RequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestManagementViewModel @Inject constructor(
    private val repository: RequestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RequestManagementUiState())
    val uiState: StateFlow<RequestManagementUiState> = _uiState.asStateFlow()

    init {
        observeRequests()
    }

    private fun observeRequests() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeServerRequests("serverId", _uiState.value.selectedFilter)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { requests ->
                    _uiState.update { it.copy(requests = requests, isLoading = false) }
                }
        }
    }

    fun onFilterSelected(status: OrderStatus?) {
        _uiState.update { it.copy(selectedFilter = status) }
        observeRequests()
    }
}
