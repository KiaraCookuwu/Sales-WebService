package com.itvo.sales.domain.usecase.customer

import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListCustomersUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(): Flow<List<Customer>> {
        return repository.getCustomers()
    }
}