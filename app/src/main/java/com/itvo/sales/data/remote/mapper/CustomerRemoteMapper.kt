package com.itvo.sales.data.remote.mapper

import com.itvo.sales.data.local.entity.CustomerEntity
import com.itvo.sales.data.remote.dto.CustomerDto
import com.itvo.sales.domain.model.Customer

object CustomerRemoteMapper {

    fun CustomerDto.toDomain(): Customer = Customer(
        id, name, email
    )

    fun CustomerDto.toEntity(): CustomerEntity = CustomerEntity(
        id, name, email,
    )

    fun Customer.toDto(): CustomerDto = CustomerDto(
        id, name, email
    )
}