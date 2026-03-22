package com.itvo.sales.domain.repository

import com.itvo.sales.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun saveCustomer(customer: Customer)
    suspend fun deleteCustomer(customerId: String)
    suspend fun findCustomerById(customerId: String): Customer?
    fun getCustomers(): Flow<List<Customer>>
}