package com.itvo.sales.presentation.customer.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.usecase.customer.DeleteCustomerUseCase
import com.itvo.sales.domain.usecase.customer.ListCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCustomerViewModel @Inject constructor(
    listCustomersUseCase: ListCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase
) : ViewModel() {

    val uiState: StateFlow<ListCustomerUiState> =
        listCustomersUseCase()
            .map { customers -> ListCustomerUiState(isLoading = false, customers = customers) }
            .onStart { emit(ListCustomerUiState(isLoading = true)) }
            .catch { emit(ListCustomerUiState(isLoading = false, customers = emptyList())) } // <- ESTA LÍNEA EVITA EL CRASH
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListCustomerUiState()
            )

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            deleteCustomerUseCase(customer)
        }
    }
}