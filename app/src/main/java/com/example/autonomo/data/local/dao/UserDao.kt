package com.example.autonomo.data.local.dao

import androidx.room.*
import com.example.autonomo.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE uid = :uid")
    fun observeUser(uid: String): Flow<User?>

    @Query("SELECT * FROM users WHERE uid = :uid")
    suspend fun getUser(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getAnyUser(): User?
}
