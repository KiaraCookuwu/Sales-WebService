package com.itvo.sales.data.repository

import com.itvo.sales.data.local.dao.ProductDao
import com.itvo.sales.data.mapper.toDomain
import com.itvo.sales.data.mapper.toEntity
import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RoomProductRepository @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return dao.getProducts()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        return dao.findByCode(productCode)?.toDomain()
    }

    override suspend fun saveProduct(product: Product) {
        dao.insert(product.toEntity())
    }

    override suspend fun deleteProduct(productCode: String) {
        dao.deleteByCode(productCode)
    }
}