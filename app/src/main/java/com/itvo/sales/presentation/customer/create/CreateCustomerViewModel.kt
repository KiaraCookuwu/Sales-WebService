package com.itvo.sales.presentation.customer.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.usecase.customer.CreateCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val createCustomerUseCase: CreateCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCustomerUiState())
    val state: StateFlow<CreateCustomerUiState> = _state

    private val _effect = Channel<CreateCustomerUiEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateCustomerUiState.() -> CreateCustomerUiState) {
        _state.update(update)
    }

    fun onEvent(event: CreateCustomerUiEvent) {
        when (event) {
            is CreateCustomerUiEvent.IdChanged -> updateState { copy(id = event.value) }
            is CreateCustomerUiEvent.NameChanged -> updateState { copy(name = event.value) }
            is CreateCustomerUiEvent.EmailChanged -> updateState { copy(email = event.value) }
            CreateCustomerUiEvent.SaveClicked -> saveCustomer()
        }
    }

    private fun saveCustomer() {
        val currentState = state.value
        if (currentState.id.isBlank() || currentState.name.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowError("ID y Nombre son requeridos"))
            return
        }

        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val customer = Customer(
                    id = currentState.id,
                    name = currentState.name,
                    email = currentState.email
                )
                createCustomerUseCase(customer)
                sendEffect(CreateCustomerUiEffect.ShowSuccess("Cliente creado con éxito"))
                sendEffect(CreateCustomerUiEffect.NavigateBack)
            } catch (e: Exception) {
                sendEffect(CreateCustomerUiEffect.ShowError(e.message ?: "Error desconocido"))
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateCustomerUiEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}