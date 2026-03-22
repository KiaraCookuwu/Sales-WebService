package com.itvo.sales.data.mapper

import com.itvo.sales.data.local.entity.ProductEntity
import com.itvo.sales.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        code,
        description,
        category,
        price,
        stock,
        taxable
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        code,
        description,
        category,
        price,
        stock,
        taxable
    )
}