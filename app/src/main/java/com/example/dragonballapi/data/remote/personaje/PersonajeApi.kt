package com.example.dragonballapi.data.remote.personaje

import com.example.dragonballapi.data.remote.model.PersonajeListRemote
import com.example.dragonballapi.data.remote.model.PersonajeRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonajeApi{
    @GET("/api/characters")
    suspend fun getPersonajeList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PersonajeListRemote>

    @GET("/api/characters/{id}")
    suspend fun getPersonajeDetail(@Path("id") id:Long): Response<PersonajeRemote>


    //Solo inicializadas hace falta implementar
    @GET("/api/characters")
    suspend fun readOneByName(@Query("name") name:String): Response<PersonajeRemote>

    @GET("/api/characters")
    suspend fun readPage(@Query("page") page: Int): Response<PersonajeRemote>
}