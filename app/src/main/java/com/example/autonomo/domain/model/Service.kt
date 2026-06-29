package com.example.autonomo.domain.model

data class Service(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val fullDescription: String,
    val providerName: String,
    val providerAvatar: String,
    val imageColor: Long,
    val rating: Float,
    val reviewCount: Int,
    val price: String,
    val schedule: String,
    val paymentMethods: List<String>,
    val warranty: String,
    val estimatedTime: String,
    val tags: List<String>
)
