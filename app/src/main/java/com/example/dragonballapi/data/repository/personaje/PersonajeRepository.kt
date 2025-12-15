package com.example.dragonballapi.data.repository.personaje

import com.example.dragonballapi.data.model.Personaje
import kotlinx.coroutines.flow.Flow

interface PersonajeRepository{
    fun observeAllPersonaje(): Flow<Result<List<Personaje>>>
    // ↑ Devuelve Flow de BD (rápido, offline-first)

    suspend fun syncPersonajeFromNetwork()
    // ↑ Sincroniza en background

    suspend fun readOne(id: Long): Result<Personaje>
    // ↑ Lee uno específico

    suspend fun addPersonaje(pokemon: Personaje)
    // ↑ Agrega uno
}