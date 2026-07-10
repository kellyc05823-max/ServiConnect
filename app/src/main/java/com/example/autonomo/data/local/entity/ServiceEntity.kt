package com.example.autonomo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.autonomo.domain.model.Service

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey val id: String,
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
    val warranty: String,
    val estimatedTime: String
)

fun ServiceEntity.toDomain(): Service = Service(
    id = id,
    name = name,
    category = category,
    description = description,
    fullDescription = fullDescription,
    providerName = providerName,
    providerAvatar = providerAvatar,
    imageColor = imageColor,
    rating = rating,
    reviewCount = reviewCount,
    price = price,
    schedule = schedule,
    paymentMethods = emptyList(), // Simplified for now
    warranty = warranty,
    estimatedTime = estimatedTime,
    tags = emptyList() // Simplified for now
)

fun Service.toEntity(): ServiceEntity = ServiceEntity(
    id = id,
    name = name,
    category = category,
    description = description,
    fullDescription = fullDescription,
    providerName = providerName,
    providerAvatar = providerAvatar,
    imageColor = imageColor,
    rating = rating,
    reviewCount = reviewCount,
    price = price,
    schedule = schedule,
    warranty = warranty,
    estimatedTime = estimatedTime
)
