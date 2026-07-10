package com.example.autonomo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.autonomo.data.local.dao.RequestDao
import com.example.autonomo.data.local.dao.ServiceDao
import com.example.autonomo.data.local.entity.RequestEntity
import com.example.autonomo.data.local.entity.ServiceEntity

@Database(entities = [ServiceEntity::class, RequestEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao
    abstract fun requestDao(): RequestDao
}
