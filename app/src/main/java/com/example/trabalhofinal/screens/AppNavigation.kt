package com.example.trabalhofinal.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabalhofinal.screens.RegisterScreen
import com.example.trabalhofinal.viewmodel.ViagemViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: ViagemViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("menu") { MenuScreen(navController, viewModel) }
        composable("nova_viagem") { NovaViagemScreen(navController, viewModel) }
        composable("register") { RegisterScreen(navController) }
        composable("sobre") { /* se criar */ }
    }
}
