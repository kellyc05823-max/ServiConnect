package com.example.autonomo.data.repository

import com.example.autonomo.data.OrderStatus
import com.example.autonomo.domain.model.Request
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    fun observeClientRequests(clientId: String): Flow<List<Request>>
    fun observeServerRequests(serverId: String, status: OrderStatus?): Flow<List<Request>>
    fun observeRequest(requestId: String): Flow<Request?>
    suspend fun createRequest(request: Request): Result<Request>
    suspend fun updateStatus(requestId: String, newStatus: OrderStatus, reason: String? = null): Result<Unit>
}
