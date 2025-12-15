package com.example.dragonballapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.dragonballapi.data.repository.personaje.PersonajeRepository
import com.example.dragonballapi.data.repository.planeta.PlanetaRepository
import com.example.dragonballapi.theme.DragonBallApiTheme
import com.example.dragonballapi.ui.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonBallApiTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

