package com.itvo.sales.presentation.product.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.itvo.sales.domain.model.Product

@Composable
fun ListProductScreen(
    viewModel: ListProductViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    var productToDelete by remember { mutableStateOf<Product?>(null) }

    if (productToDelete != null) {
        AlertDialog(
            onDismissRequest = { productToDelete = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Estás seguro de que deseas " +
                    "eliminar el producto '${productToDelete?.description}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        productToDelete?.let { viewModel.deleteProduct(it) }
                        productToDelete = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { productToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.products.isEmpty() -> {
                    EmptyProductsView()
                }
                else -> {
                    ListProduct(
                        products = uiState.products,
                        onDeleteClick = { product ->
                            productToDelete = product
                        }
                    )
                }
            }
        }
    }
}