package com.itvo.sales.data.repository

import com.itvo.sales.data.remote.datasource.CustomerFirebaseDataSource
import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreCustomerRepository @Inject constructor(
    private val firebaseDataSource: CustomerFirebaseDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return firebaseDataSource.getCustomers()
    }

    override suspend fun findCustomerById(customerId: String): Customer? {
        return firebaseDataSource.findCustomerById(customerId)
    }

    override suspend fun saveCustomer(customer: Customer) {
        firebaseDataSource.saveCustomer(customer)
    }

    override suspend fun deleteCustomer(customerId: String) {
        firebaseDataSource.deleteCustomer(customerId)
    }
}