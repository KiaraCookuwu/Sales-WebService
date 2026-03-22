package com.itvo.sales.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.itvo.sales.domain.model.Product
import com.itvo.sales.domain.usecase.product.DeleteProductUseCase
import com.itvo.sales.domain.usecase.product.ListProductsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ListProductViewModel @Inject constructor(
    getProductsUseCase: ListProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(isLoading = false, products = products)
            }
            .onStart { emit(ListProductUiState(isLoading = true)) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product)
        }
    }
}