package com.itvo.sales.domain.usecase.product

import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.repository.ProductRepository
import jakarta.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(product: Product) {

        val existing = repository.findProductByCode(product.code)

        require(existing == null) {
            "Product with code ${product.code} already exists"
        }

        repository.saveProduct(product)
    }
}