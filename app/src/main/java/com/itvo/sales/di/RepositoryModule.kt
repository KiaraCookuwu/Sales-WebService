package com.itvo.sales.di

import com.itvo.sales.data.repository.CustomerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.itvo.sales.data.repository.FirestoreProductRepository
import com.itvo.sales.domain.repository.ProductRepository
import com.itvo.sales.data.repository.ProductRepositoryImpl
import com.itvo.sales.domain.repository.CustomerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repository: ProductRepositoryImpl //RoomProductRepository// InMemoryProductRepository
    ): ProductRepository
    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
       repository: CustomerRepositoryImpl
    ): CustomerRepository
}
