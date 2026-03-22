package com.itvo.sales.presentation.product.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itvo.sales.domain.model.Product

@Composable
fun ListProduct(
    products: List<Product>,
    onDeleteClick: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = products,
            key = { it.code }
        ) { product ->
            ProductItem(
                product = product,
                onDeleteClick = onDeleteClick
            )
        }
    }
}