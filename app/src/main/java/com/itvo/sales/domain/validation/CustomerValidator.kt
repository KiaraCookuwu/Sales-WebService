package com.itvo.sales.domain.validation

import com.itvo.sales.domain.model.Customer
import com.itvo.sales.presentation.product.ValidationResult

class CustomerValidator {

    operator fun invoke(customer: Customer): ValidationResult =
        listOfNotNull(
            "ID requerido".takeIf { customer.id.isBlank() },
            "Nombre requerido".takeIf { customer.name.isBlank() },
            "Correo requerido".takeIf { customer.email.isBlank() },
            "Teléfono requerido".takeIf { customer.phone.isBlank() }
        ).firstOrNull()
            ?.let { ValidationResult.Error(it) }
            ?: ValidationResult.Success
}