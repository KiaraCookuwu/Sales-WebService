package com.itvo.sales.presentation.customer.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.itvo.sales.domain.model.Customer

@Composable
fun CustomerItem(
    customer: Customer,
    onDeleteClick: (Customer) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = customer.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "ID: ${customer.id}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Email: ${customer.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tel: ${customer.phone}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDeleteClick(customer) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ListCustomerScreen(
    viewModel: ListCustomerViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var customerToDelete by remember { mutableStateOf<Customer?>(null) }

    if (customerToDelete != null) {
        AlertDialog(
            onDismissRequest = { customerToDelete = null },
            title = { Text("Eliminar cliente") },
            text = { Text("¿Estás seguro de que deseas eliminar al cliente '${customerToDelete?.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        customerToDelete?.let { viewModel.deleteCustomer(it) }
                        customerToDelete = null
                    }
                ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { customerToDelete = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Cliente")
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
                uiState.customers.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No customers available")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = uiState.customers, key = { it.id }) { customer ->
                            CustomerItem(
                                customer = customer,
                                onDeleteClick = { customerToDelete = it }
                            )
                        }
                    }
                }
            }
        }
    }
}