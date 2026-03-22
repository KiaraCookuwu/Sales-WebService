package com.itvo.sales.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.itvo.sales.presentation.product.create.CreateProductScreen
import com.itvo.sales.presentation.product.list.ListProductScreen
import com.itvo.sales.presentation.customer.create.CreateCustomerScreen
import com.itvo.sales.presentation.customer.list.ListCustomerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Obtenemos la ruta actual para saber en qué pantalla estamos
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Definimos en qué pantallas SÍ queremos que se vea la barra de abajo
    val bottomBarRoutes = listOf("product_list", "customer_list")
    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    // Botón 1: Productos
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription =
                            "Productos") },
                        label = { Text("Productos") },
                        selected = currentRoute == "product_list",
                        onClick = {
                            navController.navigate("product_list") {
                                popUpTo(navController.graph.findStartDestination().id)
                                { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    // Botón 2: Clientes
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription =
                            "Clientes") },
                        label = { Text("Clientes") },
                        selected = currentRoute == "customer_list",
                        onClick = {
                            navController.navigate("customer_list") {
                                popUpTo(navController.graph.findStartDestination().id)
                                { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "product_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- SECCIÓN PRODUCTOS ---
            composable("product_list") {
                ListProductScreen(
                    onNavigateToCreate = { navController.navigate("create_product") }
                )
            }
            composable("create_product") {
                CreateProductScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // --- SECCIÓN CLIENTES ---
            composable("customer_list") {
                ListCustomerScreen(
                    onNavigateToCreate = { navController.navigate("create_customer") }
                )
            }
            composable("create_customer") {
                CreateCustomerScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}