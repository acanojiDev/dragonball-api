package com.example.dragonballapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dragonballapi.ui.screens.Home.HomeScreen
import com.example.dragonballapi.ui.screens.List.PersonajeListScreen
import com.example.dragonballapi.ui.screens.List.PlanetaListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home") {
            HomeScreen(navController)
        }

        composable("personajes_list") {
            PersonajeListScreen(navController)
        }

        composable("planetas_list") {
            PlanetaListScreen(navController)
        }

    }
}