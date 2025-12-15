package com.example.dragonballapi.ui.navigation

import PersonajeListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "personajes_list"  // Primera pantalla
    ) {
        composable("personajes_list") {  // Ruta
            PersonajeListScreen()        // ¿Qué mostrar?
        }
    }
}