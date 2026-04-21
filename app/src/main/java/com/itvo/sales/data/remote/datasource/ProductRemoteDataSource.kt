package com.itvo.sales.data.remote.datasource

import com.itvo.sales.data.remote.api.ApiResponse
import com.itvo.sales.data.remote.api.ProductApiService
import com.itvo.sales.data.remote.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val api: ProductApiService
) {

    suspend fun findProductByCode(code: String): ProductDto {
        return api.findProductByCode(code)
    }

    suspend fun saveProduct(product: ProductDto): ApiResponse< ProductDto >{
        return api.saveProduct(product)
    }

    suspend fun getProducts(): ApiResponse<List<ProductDto>> {
        return api.getProducts()
    }
}