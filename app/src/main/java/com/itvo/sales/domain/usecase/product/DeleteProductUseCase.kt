package com.itvo.sales.domain.usecase.product

import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.repository.ProductRepository
import jakarta.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.deleteProduct(product.code)
    }
}