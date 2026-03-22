package com.itvo.sales.domain.usecase.customer

import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import jakarta.inject.Inject

class CreateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customer: Customer) {
        val existing = repository.findCustomerById(customer.id)

        require(existing == null) {
            "El cliente con el ID ${customer.id} ya existe"
        }

        repository.saveCustomer(customer)
    }
}