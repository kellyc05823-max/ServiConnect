package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientChatsViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientChatsUiState())
    val uiState: StateFlow<ClientChatsUiState> = _uiState.asStateFlow()

    init {
        observeChats()
    }

    private fun observeChats() {
        val userId = authRepository.currentUserId ?: return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeUserChats(userId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { chats ->
                    _uiState.update { it.copy(chats = chats, isLoading = false) }
                }
        }
    }
}
