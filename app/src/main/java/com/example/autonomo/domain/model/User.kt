package com.example.autonomo.domain.model

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val photoUrl: String? = null,
    val email: String = "",
    val phone: String = "",
    val city: String = "",
    val province: String = "",
    val address: String? = null,
    val description: String = "",
    val profession: String = "",
    val specialties: List<String> = emptyList(),
    val socialMedia: Map<String, String> = emptyMap(),
    val averageRating: Float = 0f,
    val jobsCompleted: Int = 0,
    val reviewsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val lastConnection: Long = System.currentTimeMillis(),
    val isActive: Boolean = true,
    val role: UserRole = UserRole.CLIENT
)

enum class UserRole { CLIENT, PROVIDER, ADMIN }
