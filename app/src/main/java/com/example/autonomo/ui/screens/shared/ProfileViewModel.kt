package com.example.autonomo.ui.screens.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.currentUser
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { user ->
                    _uiState.update { it.copy(user = user, isLoading = false) }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
