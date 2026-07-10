package com.example.autonomo.data.repository

import com.example.autonomo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val isUserLoggedIn: Boolean
    val currentUserId: String?

    suspend fun loginWithEmail(email: String, password: String): Result<User>
    suspend fun registerWithEmail(email: String, password: String, firstName: String, lastName: String): Result<User>
    suspend fun loginWithGoogle(idToken: String): Result<User>
    suspend fun logout()
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun sendEmailVerification(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun updateProfile(user: User): Result<User>
}
