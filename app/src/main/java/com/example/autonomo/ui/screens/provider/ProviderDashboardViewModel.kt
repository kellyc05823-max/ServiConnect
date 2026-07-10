package com.example.autonomo.ui.screens.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.RequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderDashboardViewModel @Inject constructor(
    private val repository: RequestRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderDashboardUiState())
    val uiState: StateFlow<ProviderDashboardUiState> = _uiState.asStateFlow()

    init {
        observeDashboardData()
    }

    private fun observeDashboardData() {
        val providerId = authRepository.currentUserId ?: return
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            combine(
                authRepository.currentUser,
                repository.observeServerRequests(providerId, null)
            ) { user, requests ->
                ProviderDashboardUiState(
                    provider = user,
                    requests = requests,
                    isLoading = false
                )
            }.catch { e ->
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
