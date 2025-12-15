package com.example.dragonballapi.di

import com.example.dragonballapi.data.PersonajeDataSource
import com.example.dragonballapi.data.PlanetaDataSource
import com.example.dragonballapi.data.local.personaje.PersonajeLocalDataSource
import com.example.dragonballapi.data.local.planeta.PlanetaLocalDataSource

import com.example.dragonballapi.data.remote.personaje.PersonajeRemoteDataSource
import com.example.dragonballapi.data.remote.planeta.PlanetaRemoteDataSource
import com.example.dragonballapi.data.repository.personaje.PersonajeRepository
import com.example.dragonballapi.data.repository.personaje.PersonajeRepositoryImpl
import com.example.dragonballapi.data.repository.planeta.PlanetaRepository
import com.example.dragonballapi.data.repository.planeta.PlanetaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindsRemotePersonajeRemoteDataSource(ds: PersonajeRemoteDataSource): PersonajeDataSource

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindsLocalPersonajeRemoteDataSource(ds: PersonajeLocalDataSource): PersonajeDataSource

    @Binds
    @Singleton
    abstract fun bindPersonajeRepository(repository: PersonajeRepositoryImpl): PersonajeRepository
    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository

    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindRemotePlanetaRemoteDataSource(ds: PlanetaRemoteDataSource): PlanetaDataSource

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindLocalPlanetaRemoteDataSource(ds: PlanetaLocalDataSource): PlanetaDataSource

    @Binds
    @Singleton
    abstract fun bindPlanetaRepository(repository: PlanetaRepositoryImpl): PlanetaRepository
    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource