package com.example.dragonballapi.di

import com.example.dragonballapi.data.remote.personaje.PersonajeApi
import com.example.dragonballapi.data.remote.planeta.PlanetaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    @Singleton
    fun provideDragonBall(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("https://www.dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePersonajeApi(retrofit: Retrofit): PersonajeApi{
        return retrofit.create(PersonajeApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlanetaApi(retrofit: Retrofit): PlanetaApi{
        return retrofit.create(PlanetaApi::class.java)
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope{
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}