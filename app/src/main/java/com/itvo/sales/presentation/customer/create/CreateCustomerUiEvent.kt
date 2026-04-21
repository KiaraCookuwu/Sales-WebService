package com.itvo.sales.presentation.customer.create

sealed class CreateCustomerUiEvent {
    data class IdChanged(val value: String) : CreateCustomerUiEvent()
    data class NameChanged(val value: String) : CreateCustomerUiEvent()
    data class EmailChanged(val value: String) : CreateCustomerUiEvent()
    object SaveClicked : CreateCustomerUiEvent()
}