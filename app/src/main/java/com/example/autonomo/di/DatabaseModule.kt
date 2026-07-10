package com.example.autonomo.di

import android.content.Context
import androidx.room.Room
import com.example.autonomo.data.local.AppDatabase
import com.example.autonomo.data.local.dao.RequestDao
import com.example.autonomo.data.local.dao.ServiceDao
import dagger.Module
import dagger.InstallIn
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "serviconnect_db"
        ).build()
    }

    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao = db.serviceDao()

    @Provides
    fun provideRequestDao(db: AppDatabase): RequestDao = db.requestDao()
}
