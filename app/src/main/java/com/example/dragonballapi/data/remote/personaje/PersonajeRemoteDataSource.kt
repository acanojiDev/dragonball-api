package com.example.dragonballapi.data.remote.personaje

import com.example.dragonballapi.data.PersonajeDataSource
import com.example.dragonballapi.data.model.Personaje
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.Long


class  PersonajeRemoteDataSource @Inject constructor(
    private val personajeApi: PersonajeApi,
    private val scope: CoroutineScope
): PersonajeDataSource{
    override suspend fun addAll(personajeList: List<Personaje>) {
        //no implementado para api
    }

    override fun observe(): Flow<Result<List<Personaje>>> {
        return flow{
            emit(Result.success(listOf<Personaje>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Personaje>> {
        return try {
            val response = personajeApi.getPersonajeList(limit = 40)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val personajeList = mutableListOf<Personaje>()

                    for (item in body.items) {  // ✅ Cambiar de "results" a "items"
                        val detailResponse = personajeApi.getPersonajeDetail(item.id)
                        if (detailResponse.isSuccessful) {
                            val detail = detailResponse.body()
                            if (detail != null) {
                                personajeList.add(
                                    Personaje(
                                        id = detail.id,
                                        name = detail.name,
                                        ki = detail.ki,
                                        race = detail.race,
                                        gender = detail.gender,
                                        description = detail.description,
                                        image = detail.image
                                    )
                                )
                            }
                        }
                    }

                    Result.success(personajeList)
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

    override suspend fun readOne(id: Long): Result<Personaje> {
        return try {
            val response = personajeApi.getPersonajeDetail(id)
            if (response.isSuccessful) {
                val detail = response.body()
                if (detail != null) {
                    Result.success(
                        Personaje(
                            id = detail.id,
                            name = detail.name,
                            ki = detail.ki,
                            race = detail.race,
                            gender = detail.gender,
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
        TODO("Not yet implemented")
    }

//    private suspend fun readOneByName(name:String): Personaje?{
//        val response = personajeApi.readOneByName(name)
//        return response.body()?.let { personajeList ->
//            personajeList.firstOrNull()?
//        }
//    }
}