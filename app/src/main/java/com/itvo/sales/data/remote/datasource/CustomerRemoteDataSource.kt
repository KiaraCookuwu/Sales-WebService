package com.itvo.sales.data.remote.datasource

import com.itvo.sales.data.remote.api.ApiResponse
import com.itvo.sales.data.remote.api.CustomerApiService
import com.itvo.sales.data.remote.dto.CustomerDto
import javax.inject.Inject

class CustomerRemoteDataSource @Inject constructor(
    private val api: CustomerApiService
) {
    suspend fun findCustomerById(id: String): CustomerDto {
        return api.findCustomerById(id)
    }

    suspend fun saveCustomer(customer: CustomerDto): CustomerDto {
        return api.saveCustomer(customer)
    }

    suspend fun getCustomers(): ApiResponse<List<CustomerDto>> {
        return api.getCustomers()
    }
}