package com.itvo.sales.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.itvo.sales.data.local.repository.RoomCustomerRepository
import com.itvo.sales.data.local.repository.RoomProductRepository
import com.itvo.sales.data.remote.FirestoreProductRepository
import com.itvo.sales.domain.repository.CustomerRepository
import com.itvo.sales.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repository: FirestoreProductRepository //InMemory / Room / Firestore
    ): ProductRepository

    // NUEVO: Enlazamos el repositorio de Customer
    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: RoomCustomerRepository
    ): CustomerRepository
}