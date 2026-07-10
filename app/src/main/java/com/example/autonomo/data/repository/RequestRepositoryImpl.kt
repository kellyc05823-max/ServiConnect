package com.example.autonomo.data.repository

import com.example.autonomo.data.OrderStatus
import com.example.autonomo.data.local.dao.RequestDao
import com.example.autonomo.data.local.entity.toDomain
import com.example.autonomo.data.local.entity.toEntity
import com.example.autonomo.domain.model.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val dao: RequestDao
) : RequestRepository {

    override fun observeClientRequests(clientId: String): Flow<List<Request>> =
        dao.observeClientRequests(clientId)
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(Dispatchers.IO)

    override fun observeServerRequests(serverId: String, status: OrderStatus?): Flow<List<Request>> =
        dao.observeServerRequests(status)
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(Dispatchers.IO)

    override fun observeRequest(requestId: String): Flow<Request?> =
        dao.observeRequestById(requestId)
            .map { it?.toDomain() }
            .flowOn(Dispatchers.IO)

    override suspend fun createRequest(request: Request): Result<Request> =
        withContext(Dispatchers.IO) {
            try {
                dao.insertRequest(request.toEntity())
                Result.success(request)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun updateStatus(
        requestId: String,
        newStatus: OrderStatus,
        reason: String?
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            dao.updateStatus(requestId, newStatus)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
