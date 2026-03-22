package com.itvo.sales.data.local.repository

import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl: ProductRepository {
    override suspend fun saveProduct(product: Product) {
        TODO("Not yet implemented")
    }

    fun findByCode(productCode: String): Product {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(productCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        TODO("Not yet implemented")
    }

    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    fun getProduct(): List<Product> {
        TODO("Not yet implemented")
    }

}