package com.example.autonomo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.domain.model.Request

@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey val id: String,
    val clientId: String,
    val clientName: String,
    val serviceId: String,
    val serviceName: String,
    val status: OrderStatus,
    val date: String
)

fun RequestEntity.toDomain(): Request = Request(
    id = id,
    clientId = clientId,
    clientName = clientName,
    serviceId = serviceId,
    serviceName = serviceName,
    status = status,
    date = date,
    formData = emptyMap() // Simplified
)

fun Request.toEntity(): RequestEntity = RequestEntity(
    id = id,
    clientId = clientId,
    clientName = clientName,
    serviceId = serviceId,
    serviceName = serviceName,
    status = status,
    date = date
)
