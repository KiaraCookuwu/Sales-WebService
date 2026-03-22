package com.itvo.sales.presentation.product.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.usecase.product.CreateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProductUiState())
    val state: StateFlow<CreateProductUiState> = _state

    private val _effect = Channel<CreateProductUiEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateProductUiState.() -> CreateProductUiState) {
        _state.update(update)
    }

    fun onEvent(event: CreateProductUiEvent) {
        when (event) {

            is CreateProductUiEvent.CodeChanged ->
                updateState { copy(code = event.value) }

            is CreateProductUiEvent.DescriptionChanged ->
                updateState { copy(description = event.value) }

            is CreateProductUiEvent.CategoryChanged ->
                updateState { copy(category = event.value) }

            is CreateProductUiEvent.PriceChanged ->
                updateState { copy(price = event.value) }

            is CreateProductUiEvent.StockChanged ->
                updateState { copy(stock = event.value) }

            is CreateProductUiEvent.TaxableChanged ->
                updateState { copy(taxable = event.value) }

            CreateProductUiEvent.SaveClicked ->
                saveProduct()
        }
    }

    private fun saveProduct() {

        val currentState = state.value

        if (currentState.code.isBlank()) {
            sendEffect(CreateProductUiEffect.ShowError("Code required"))
            return
        }

        viewModelScope.launch {

            updateState { copy(isLoading = true) }

            try {

                val product = Product(
                    code = currentState.code,
                    description = currentState.description,
                    category = currentState.category,
                    price = currentState.price.toDouble()     ,
                    stock = currentState.stock.toInt(),
                    taxable = currentState.taxable
                )

                createProductUseCase(product)

                sendEffect(CreateProductUiEffect.ShowSuccess("Product created"))
                sendEffect(CreateProductUiEffect.NavigateBack)

            } catch (e: Exception) {
                sendEffect(
                    CreateProductUiEffect.ShowError(
                        e.message ?: "Unknown error"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateProductUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}