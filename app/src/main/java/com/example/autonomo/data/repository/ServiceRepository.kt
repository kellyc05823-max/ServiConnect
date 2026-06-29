package com.example.autonomo.data.repository

import com.example.autonomo.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun observeServices(query: String? = null, category: String? = null): Flow<List<Service>>
    fun observeService(serviceId: String): Flow<Service?>
    suspend fun createService(service: Service): Result<Service>
}
