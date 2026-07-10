package com.example.autonomo.data.local.dao

import androidx.room.*
import com.example.autonomo.data.local.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun observeServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :serviceId")
    fun observeServiceById(serviceId: String): Flow<ServiceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<ServiceEntity>)
}
