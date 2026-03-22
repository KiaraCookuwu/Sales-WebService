package com.itvo.sales.presentation.customer.create

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateCustomerScreen(
    viewModel: CreateCustomerViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateCustomerUiEffect.NavigateBack -> onNavigateBack()
                is CreateCustomerUiEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
                is CreateCustomerUiEffect.ShowSuccess -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.id,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.IdChanged(it)) },
                label = { Text("ID / RFC") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.NameChanged(it)) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.EmailChanged(it)) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = state.phone,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.PhoneChanged(it)) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Button(
                onClick = { viewModel.onEvent(CreateCustomerUiEvent.SaveClicked) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cliente")
            }
        }
    }
}