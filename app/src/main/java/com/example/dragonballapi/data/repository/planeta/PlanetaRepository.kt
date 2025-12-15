package com.example.dragonballapi.data.repository.planeta

import com.example.dragonballapi.data.model.Planeta
import kotlinx.coroutines.flow.Flow

interface PlanetaRepository{
    fun observeAllPlaneta(): Flow<Result<List<Planeta>>>
    // ↑ Devuelve Flow de BD (rápido, offline-first)

    suspend fun syncPlanetaFromNetwork()
    // ↑ Sincroniza en background

    suspend fun readOne(id: Long): Result<Planeta>
    // ↑ Lee uno específico

    suspend fun addPlaneta(planeta: Planeta)
    // ↑ Agrega uno

    suspend fun updatePlaneta(planeta: Planeta)

    suspend fun deletePlaneta(id:Long)
}