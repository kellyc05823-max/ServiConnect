package com.example.autonomo.di

import com.example.autonomo.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRequestRepository(
        impl: FakeRequestRepository
    ): RequestRepository

    @Binds
    @Singleton
    abstract fun bindServiceRepository(
        impl: FakeServiceRepository
    ): ServiceRepository
}
