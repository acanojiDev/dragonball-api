package com.example.dragonballapi.data.remote.planeta

import com.example.dragonballapi.data.PlanetaDataSource
import com.example.dragonballapi.data.model.Planeta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class  PlanetaRemoteDataSource @Inject constructor(
    private val planetaApi: PlanetaApi,
    private val scope: CoroutineScope
): PlanetaDataSource{
    override suspend fun addAll(planetaList: List<Planeta>) {
        //En remoto no podemos añadir
    }

    override fun observe(): Flow<Result<List<Planeta>>> {
        return flow{
            emit(Result.success(listOf<Planeta>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Planeta>> {
        return try {
            val response = planetaApi.getPlanetaList(limit = 40)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val planetaList = mutableListOf<Planeta>()

                    for (item in body.items) {  // ✅ Cambiar de "results" a "items"
                        val detailResponse = planetaApi.getPlanetaDetail(item.id)
                        if (detailResponse.isSuccessful) {
                            val detail = detailResponse.body()
                            if (detail != null) {
                                planetaList.add(
                                    Planeta(
                                        id = detail.id,
                                        name = detail.name,
                                        isDestroyed = detail.isDestroyed,
                                        description = detail.description,
                                        image = detail.image
                                    )
                                )
                            }
                        }
                    }

                    Result.success(planetaList)
                } else {
                    Result.failure(Exception("Body es null"))
                }
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun readOne(id: Long): Result<Planeta> {
        return try {
            val response = planetaApi.getPlanetaDetail(id)
            if (response.isSuccessful) {
                val detail = response.body()
                if (detail != null) {
                    Result.success(
                        Planeta(
                            id = detail.id,
                            name = detail.name,
                            isDestroyed = detail.isDestroyed,
                            description = detail.description,
                            image = detail.image
                        )
                    )
                } else {
                    Result.failure(Exception("Detail es null"))
                }
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isError() {
        //TODO("Not yet implemented")
    }

}