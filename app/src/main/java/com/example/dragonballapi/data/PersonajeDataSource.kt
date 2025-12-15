package com.example.dragonballapi.data

import com.example.dragonballapi.data.model.Personaje
import kotlinx.coroutines.flow.Flow

interface PersonajeDataSource {
    suspend fun addAll(personajeList: List<Personaje>)
    // ↑ Todos deben poder guardar pokémon

    fun observe(): Flow<Result<List<Personaje>>>
    // ↑ Todos deben poder observar cambios

    suspend fun readAll(): Result<List<Personaje>>
    // ↑ Todos deben poder leer todos

    suspend fun readOne(id: Long): Result<Personaje>
    // ↑ Todos deben poder leer uno específico


    suspend fun isError()
    // ↑ Todos deben poder reportar errores
}