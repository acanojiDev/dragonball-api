package com.example.dragonballapi.data.local.personaje

class PersonajeNotFoundException(
    message: String = "Personaje no encontrado"
): RuntimeException(message)