package com.example.autonomo.ui.screens.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.RequestRepository
import com.example.autonomo.data.repository.ServiceRepository
import com.example.autonomo.domain.model.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ServiceRequestViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val requestRepository: RequestRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceRequestUiState())
    val uiState: StateFlow<ServiceRequestUiState> = _uiState.asStateFlow()

    fun loadService(serviceId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            serviceRepository.observeService(serviceId)
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { service ->
                    _uiState.update { it.copy(service = service, isLoading = false) }
                }
        }
    }

    fun submitRequest(observations: String) {
        val service = _uiState.value.service ?: return
        val userId = authRepository.currentUserId ?: return
        
        _uiState.update { it.copy(isSubmitting = true) }
        
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            val request = Request(
                id = UUID.randomUUID().toString(),
                clientId = userId,
                clientName = "${user?.firstName} ${user?.lastName}",
                providerId = service.ownerId,
                providerName = service.ownerName,
                serviceId = service.id,
                serviceName = service.name,
                price = service.price,
                observations = observations,
                createdAt = System.currentTimeMillis()
            )
            
            requestRepository.createRequest(request)
                .onSuccess {
                    _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isSubmitting = false, error = e.message) }
                }
        }
    }
}
