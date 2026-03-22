package com.itvo.sales.presentation.customer.create

data class CreateCustomerUiState(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false
)