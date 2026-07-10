package com.example.autonomo.ui.screens.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.ChatRepository
import com.example.autonomo.domain.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatDetailUiState())
    val uiState: StateFlow<ChatDetailUiState> = _uiState.asStateFlow()

    private var currentChatId: String? = null

    init {
        _uiState.update { it.copy(currentUserId = authRepository.currentUserId) }
    }

    fun loadMessages(chatId: String) {
        currentChatId = chatId
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeMessages(chatId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { messages ->
                    _uiState.update { it.copy(messages = messages, isLoading = false) }
                }
        }
    }

    fun sendMessage(text: String) {
        val chatId = currentChatId ?: return
        val userId = authRepository.currentUserId ?: return
        
        viewModelScope.launch {
            val message = Message(
                id = UUID.randomUUID().toString(),
                senderId = userId,
                text = text,
                timestamp = System.currentTimeMillis()
            )
            repository.sendMessage(chatId, message)
        }
    }
}
