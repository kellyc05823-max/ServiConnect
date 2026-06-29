package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientHomeViewModel @Inject constructor(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientHomeUiState())
    val uiState: StateFlow<ClientHomeUiState> = _uiState.asStateFlow()

    init {
        observeServices()
    }

    private fun observeServices() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeServices(_uiState.value.searchQuery, _uiState.value.selectedCategory)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { services ->
                    _uiState.update { it.copy(services = services, isLoading = false) }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        observeServices()
    }

    fun onCategorySelected(category: String?) {
        _uiState.update { it.copy(selectedCategory = category) }
        observeServices()
    }
}
