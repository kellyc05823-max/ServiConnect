package com.example.autonomo.data.repository

import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.domain.model.Request
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRequestRepository @Inject constructor() : RequestRepository {

    private val _requests = MutableStateFlow<List<Request>>(
        MockData.providerRequests.map { order ->
            Request(
                id = order.id,
                clientId = "c1",
                clientName = order.providerName, // In provider requests, providerName field was used for client
                serviceId = order.serviceId,
                serviceName = order.serviceName,
                status = order.status,
                date = order.date
            )
        }
    )

    override fun observeClientRequests(clientId: String): Flow<List<Request>> =
        _requests.map { list -> list.filter { it.clientId == clientId } }
            .onStart { delay(800) }

    override fun observeServerRequests(serverId: String, status: OrderStatus?): Flow<List<Request>> =
        _requests.map { list ->
            list.filter { status == null || it.status == status }
        }.onStart { delay(800) }

    override fun observeRequest(requestId: String): Flow<Request?> =
        _requests.map { list -> list.find { it.id == requestId } }

    override suspend fun createRequest(request: Request): Result<Request> {
        delay(600)
        _requests.update { it + request }
        return Result.success(request)
    }

    override suspend fun updateStatus(
        requestId: String,
        newStatus: OrderStatus,
        reason: String?
    ): Result<Unit> {
        delay(500)
        _requests.update { list ->
            list.map { if (it.id == requestId) it.copy(status = newStatus) else it }
        }
        return Result.success(Unit)
    }
}
