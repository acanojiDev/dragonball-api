package com.example.dragonballapi.data.remote.planeta

import com.example.dragonballapi.data.remote.model.PlanetaListRemote
import com.example.dragonballapi.data.remote.model.PlanetaRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanetaApi{
    @GET("/api/planets")
    suspend fun getPlanetaList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PlanetaListRemote>

    @GET("/api/planets/{id}")
    suspend fun getPlanetaDetail(@Path("id") id:Long): Response<PlanetaRemote>
}