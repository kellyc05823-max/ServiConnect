package com.example.autonomo.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.loginWithEmail(email, pass)
                .onSuccess { user ->
                    _uiState.update { it.copy(user = user, isLoading = false, isSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
        }
    }

    fun register(email: String, pass: String, first: String, last: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.registerWithEmail(email, pass, first, last)
                .onSuccess { user ->
                    _uiState.update { it.copy(user = user, isLoading = false, isSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.update { AuthUiState() }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
