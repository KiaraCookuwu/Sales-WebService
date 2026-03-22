package com.itvo.sales.presentation.customer.list

import com.itvo.sales.domain.model.Customer

data class ListCustomerUiState(
    val isLoading: Boolean = false,
    val customers: List<Customer> = emptyList()
)