package com.itvo.sales.presentation.product.list

import com.itvo.sales.domain.model.Product

data class ListProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)