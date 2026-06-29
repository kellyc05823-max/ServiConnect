package com.example.autonomo.data.repository

import com.example.autonomo.data.MockData
import com.example.autonomo.domain.model.Service
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeServiceRepository @Inject constructor() : ServiceRepository {

    private val _services = MutableStateFlow<List<Service>>(
        MockData.services.map { s ->
            Service(
                id = s.id,
                name = s.name,
                category = s.category,
                description = s.description,
                fullDescription = s.fullDescription,
                providerName = s.providerName,
                providerAvatar = s.providerAvatar,
                imageColor = s.imageColor,
                rating = s.rating,
                reviewCount = s.reviewCount,
                price = s.price,
                schedule = s.schedule,
                paymentMethods = s.paymentMethods,
                warranty = s.warranty,
                estimatedTime = s.estimatedTime,
                tags = s.tags
            )
        }
    )

    override fun observeServices(query: String?, category: String?): Flow<List<Service>> =
        _services.map { list ->
            list.filter { s ->
                (category == null || s.category == category) &&
                (query.isNullOrBlank() || s.name.contains(query, ignoreCase = true))
            }
        }.onStart { delay(600) }

    override fun observeService(serviceId: String): Flow<Service?> =
        _services.map { list -> list.find { it.id == serviceId } }

    override suspend fun createService(service: Service): Result<Service> {
        delay(800)
        _services.update { it + service }
        return Result.success(service)
    }
}
