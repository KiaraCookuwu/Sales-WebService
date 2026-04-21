package com.itvo.sales.data.local.datasource

import com.itvo.sales.data.local.dao.CustomerDao
import com.itvo.sales.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerLocalDataSource @Inject constructor(
    private val dao: CustomerDao
) {
    fun getCustomers(): Flow<List<CustomerEntity>> {
        return dao.getCustomers()
    }

    suspend fun findCustomerById(id: String): CustomerEntity? {
        return dao.findById(id)
    }

    suspend fun saveCustomer(customer: CustomerEntity) {
        dao.insert(customer)
    }

    suspend fun deleteCustomer(id: String) {
        dao.deleteById(id)
    }

    suspend fun saveCustomers(customers: List<CustomerEntity>) {
        dao.insertAll(customers)
    }

    suspend fun replaceAll(customers: List<CustomerEntity>){
        dao.replaceAll(customers)
    }
}