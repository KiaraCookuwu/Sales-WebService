package com.itvo.sales.data.mapper

import com.itvo.sales.data.local.entity.CustomerEntity
import com.itvo.sales.domain.model.Customer

fun CustomerEntity.toDomain(): Customer {
    return Customer(
        id = id,
        name = name,
        email = email,
        phone = phone
    )
}

fun Customer.toEntity(): CustomerEntity {
    return CustomerEntity(
        id = id,
        name = name,
        email = email,
        phone = phone
    )
}