package com.example.autonomo.ui.screens.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autonomo.data.repository.AuthRepository
import com.example.autonomo.data.repository.ServiceRepository
import com.example.autonomo.domain.model.GeoPoint
import com.example.autonomo.domain.model.PriceType
import com.example.autonomo.domain.model.Service
import com.example.autonomo.domain.model.ServiceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _serviceDraft = MutableStateFlow(Service())
    val serviceDraft = _serviceDraft.asStateFlow()

    private val _isPublished = MutableStateFlow(false)
    val isPublished = _isPublished.asStateFlow()

    fun updateBasicInfo(name: String, category: String, description: String) {
        _serviceDraft.update { 
            it.copy(name = name, category = category, description = description) 
        }
    }

    fun updatePricing(price: Double, priceType: PriceType, schedules: String) {
        _serviceDraft.update { 
            it.copy(price = price, priceType = priceType, schedules = schedules) 
        }
    }
    
    fun updateLocation(city: String, province: String) {
        _serviceDraft.update {
            it.copy(city = city, province = province)
        }
    }

    fun publishService() {
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            val currentService = _serviceDraft.value.copy(
                id = UUID.randomUUID().toString(),
                ownerId = authRepository.currentUserId ?: "",
                ownerName = "${user?.firstName} ${user?.lastName}",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                status = ServiceStatus.ACTIVE
            )
            
            serviceRepository.createService(currentService)
                .onSuccess {
                    _isPublished.value = true
                }
        }
    }
}
