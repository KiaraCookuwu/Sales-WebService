package com.itvo.sales.data.repository

import android.util.Log
import com.itvo.sales.data.local.datasource.CustomerLocalDataSource
import com.itvo.sales.data.mapper.toDomain
import com.itvo.sales.data.mapper.toEntity
import com.itvo.sales.data.remote.datasource.CustomerRemoteDataSource
import com.itvo.sales.data.remote.mapper.CustomerRemoteMapper.toDomain
import com.itvo.sales.data.remote.mapper.CustomerRemoteMapper.toDto
import com.itvo.sales.data.remote.mapper.CustomerRemoteMapper.toEntity
import com.itvo.sales.domain.model.Customer
import com.itvo.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remote: CustomerRemoteDataSource,
    private val local: CustomerLocalDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> = flow {

        // 🔥 1. Intentar actualizar desde API
        try {
            // Recibimos el paquete completo
            val response = remote.getCustomers()

            // Verificamos si la API nos dio luz verde
            if (response.success) {
                // Sacamos la lista que viene adentro de "data" y la transformamos
                val customers = response.data.map { it.toDomain() }

                Log.d("CUSTOMERS_OK", "Llegaron los datos: ${customers.size} clientes")

                // Guardamos en la base de datos local
                local.replaceAll(customers.map { it.toEntity() })
            } else {
                Log.e("CUSTOMERS_API", "La API respondió, pero con success = false")
            }

        } catch (e: Exception) {
            Log.e("CUSTOMERS_ERROR", "Error de red o de conversión: ${e.message}", e)
        }

        // 🔥 2. Emitir datos locales (flow infinito)
        emitAll(
            local.getCustomers()
                .map { list -> list.map { it.toDomain() } }
        )
    }

    override suspend fun findCustomerById(customerId: String): Customer? {

        // 1. Buscar local primero
        val localCustomer = local.findCustomerById(customerId)
        if (localCustomer != null) {
            return localCustomer.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteCustomer = remote.findCustomerById(customerId)

            // guardar en local
            local.saveCustomer(remoteCustomer.toEntity())

            remoteCustomer.toDomain()

        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomer(customer: Customer) {
        try {
            remote.saveCustomer(customer.toDto())
            local.saveCustomer(customer.toEntity())
        } catch (e: Exception) {
            local.saveCustomer(customer.toEntity())
        }
    }

    override suspend fun deleteCustomer(customerId: String) {
        local.deleteCustomer(customerId)
    }
}