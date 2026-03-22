package com.itvo.sales.domain.usecase.customer

import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import jakarta.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customer: Customer) {
        repository.deleteCustomer(customer.id)
    }
}