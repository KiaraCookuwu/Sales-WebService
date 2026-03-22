package com.itvo.sales.data.local.repository

import com.itvo.sales.data.local.dao.CustomerDao
import com.itvo.sales.data.mapper.toDomain
import com.itvo.sales.data.mapper.toEntity
import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCustomerRepository @Inject constructor(
    private val dao: CustomerDao
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return dao.getCustomers()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findCustomerById(customerId: String): Customer? {
        return dao.findById(customerId)?.toDomain()
    }

    override suspend fun saveCustomer(customer: Customer) {
        dao.insert(customer.toEntity())
    }

    override suspend fun deleteCustomer(customerId: String) {
        dao.deleteById(customerId)
    }
}