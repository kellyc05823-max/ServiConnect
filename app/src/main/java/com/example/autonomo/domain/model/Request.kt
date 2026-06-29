package com.example.autonomo.domain.model

import com.example.autonomo.data.OrderStatus

data class Request(
    val id: String,
    val clientId: String,
    val clientName: String,
    val serviceId: String,
    val serviceName: String,
    val status: OrderStatus,
    val date: String,
    val formData: Map<String, String> = emptyMap()
)
