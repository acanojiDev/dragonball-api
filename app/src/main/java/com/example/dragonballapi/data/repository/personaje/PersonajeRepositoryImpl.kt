package com.example.dragonballapi.data.repository.personaje

import com.example.dragonballapi.data.local.personaje.PersonajeLocalDataSource
import com.example.dragonballapi.data.model.Personaje
import com.example.dragonballapi.data.remote.personaje.PersonajeRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonajeRepositoryImpl @Inject constructor(
    private val localDataSource: PersonajeLocalDataSource,
    private val remoteDataSource: PersonajeRemoteDataSource
): PersonajeRepository {
    override fun observeAllPersonaje(): Flow<Result<List<Personaje>>> {
        return localDataSource.observe()
    }

    override suspend fun syncPersonajeFromNetwork() {
        val result = remoteDataSource.readAll()
        result.onSuccess { personajeList ->
            localDataSource.refreshAll(personajeList)
        }.onFailure { exception ->
            // Si falla (sin internet): no pasa nada
            // BD se mantiene igual, usuario sigue viendo datos viejos
        }
    }

    override suspend fun readOne(id: Long): Result<Personaje> {
        return localDataSource.readOne(id)
    }

    override suspend fun addPersonaje(personaje: Personaje) {
        localDataSource.addAll(listOf(personaje))
    }
}