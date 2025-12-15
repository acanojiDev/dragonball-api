package com.example.dragonballapi.data

import com.example.dragonballapi.data.model.Planeta
import kotlinx.coroutines.flow.Flow

interface PlanetaDataSource {
    suspend fun addAll(planetaList: List<Planeta>)
    // ↑ Todos deben poder guardar pokémon

    fun observe(): Flow<Result<List<Planeta>>>
    // ↑ Todos deben poder observar cambios

    suspend fun readAll(): Result<List<Planeta>>
    // ↑ Todos deben poder leer todos

    suspend fun readOne(id: Long): Result<Planeta>
    // ↑ Todos deben poder leer uno específico

    suspend fun isError()
    // ↑ Todos deben poder reportar errores
}