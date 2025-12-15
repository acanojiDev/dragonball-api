package com.example.dragonballapi.data.local.planeta

class PlanetaNotFoundException(
    message: String = "Planeta no encontrado"
): RuntimeException(message)