package com.example.dragonballapi.data.local.planeta

import com.example.dragonballapi.data.PlanetaDataSource
import com.example.dragonballapi.data.model.Planeta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanetaLocalDataSource @Inject constructor(
    private val planetaDao: PlanetaDao
): PlanetaDataSource {
    override suspend fun addAll(planetaList: List<Planeta>) {
        planetaDao.insert(planetaList.toEntity())
    }

    override fun observe(): Flow<Result<List<Planeta>>> {
        return planetaDao.observeAll()
            .map{entities ->
                try {
                    Result.success(entities.toModel())
                }catch (e: Exception){
                    Result.failure(e)
                }
            }
            .catch { error ->
                emit(Result.failure(error))
            }
    }

    override suspend fun readAll(): Result<List<Planeta>> {
        return try{
            val entities = planetaDao.getAll()
            Result.success(entities.toModel())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun readOne(id: Long): Result<Planeta> {
        return try{
            val entity = planetaDao.readPlanetaById(id)
            if(entity != null){
                Result.success(entity.toModel())
            }else{
                Result.failure(PlanetaNotFoundException())
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    suspend fun refreshAll(planetaList: List<Planeta>){
        planetaDao.deleteAll()
        planetaDao.insert(planetaList.toEntity())
    }

}