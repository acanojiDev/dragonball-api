package com.example.dragonballapi.data.repository.planeta


import com.example.dragonballapi.data.local.planeta.PlanetaLocalDataSource
import com.example.dragonballapi.data.model.Planeta
import com.example.dragonballapi.data.remote.planeta.PlanetaRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanetaRepositoryImpl @Inject constructor(
    private val localDataSource: PlanetaLocalDataSource,
    private val remoteDataSource: PlanetaRemoteDataSource
): PlanetaRepository{
    override fun observeAllPlaneta(): Flow<Result<List<Planeta>>> {
        return localDataSource.observe()
    }

    override suspend fun syncPlanetaFromNetwork() {
        val result = remoteDataSource.readAll()

        result.onSuccess { planetaList ->
            localDataSource.refreshAll(planetaList)
        }.onFailure { exception ->
            // Si falla (sin internet): no pasa nada
            // BD se mantiene igual, usuario sigue viendo datos viejos
        }
    }

    override suspend fun readOne(id: Long): Result<Planeta> {
        return localDataSource.readOne(id)
    }

    override suspend fun addPlaneta(planeta: Planeta) {
        localDataSource.addAll(listOf(planeta))
    }

    override suspend fun updatePlaneta(planeta:Planeta){
        localDataSource.addAll(listOf(planeta))
    }

    override suspend fun deletePlaneta(id:Long){
        localDataSource.deleteById(id)
    }

}