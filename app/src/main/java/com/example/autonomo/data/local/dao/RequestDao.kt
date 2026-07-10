package com.example.autonomo.data.local.dao

import androidx.room.*
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.data.local.entity.RequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {
    @Query("SELECT * FROM requests WHERE clientId = :clientId")
    fun observeClientRequests(clientId: String): Flow<List<RequestEntity>>

    @Query("SELECT * FROM requests WHERE status = :status OR :status IS NULL")
    fun observeServerRequests(status: OrderStatus?): Flow<List<RequestEntity>>

    @Query("SELECT * FROM requests WHERE id = :requestId")
    fun observeRequestById(requestId: String): Flow<RequestEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestEntity)

    @Query("UPDATE requests SET status = :newStatus WHERE id = :requestId")
    suspend fun updateStatus(requestId: String, newStatus: OrderStatus)
}
