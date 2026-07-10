package com.example.autonomo.data.repository

import com.example.autonomo.data.local.dao.ServiceDao
import com.example.autonomo.data.local.entity.toDomain
import com.example.autonomo.data.local.entity.toEntity
import com.example.autonomo.domain.model.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val dao: ServiceDao
) : ServiceRepository {

    override fun observeServices(query: String?, category: String?): Flow<List<Service>> =
        dao.observeServices()
            .map { entities -> 
                entities.map { it.toDomain() }
                    .filter { s ->
                        (category == null || s.category == category) &&
                        (query.isNullOrBlank() || s.name.contains(query, ignoreCase = true))
                    }
            }
            .flowOn(Dispatchers.IO)

    override fun observeService(serviceId: String): Flow<Service?> =
        dao.observeServiceById(serviceId)
            .map { it?.toDomain() }
            .flowOn(Dispatchers.IO)

    override suspend fun createService(service: Service): Result<Service> =
        withContext(Dispatchers.IO) {
            try {
                dao.insertService(service.toEntity())
                Result.success(service)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
