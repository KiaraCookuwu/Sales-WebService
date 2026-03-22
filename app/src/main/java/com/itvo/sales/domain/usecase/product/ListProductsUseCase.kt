package com.itvo.sales.domain.usecase.product

import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
    }
}