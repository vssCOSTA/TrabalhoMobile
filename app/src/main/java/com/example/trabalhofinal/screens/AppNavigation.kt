package com.example.trabalhofinal.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("menu") { MenuScreen(navController) }
        composable("nova_viagem") { NovaViagemScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("sobre") { /* se criar */ }

        composable(
            route = "editar_viagem/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            NovaViagemScreen(navController = navController, viagemId = id)
        }
    }
}
