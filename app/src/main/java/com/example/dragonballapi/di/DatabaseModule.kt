package com.example.dragonballapi.di

import android.content.Context
import androidx.room.Room
import com.example.dragonballapi.data.local.DragonBallDatabase
import com.example.dragonballapi.data.local.personaje.PersonajeDao
import com.example.dragonballapi.data.local.planeta.PlanetaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // ← Crea una sola instancia para toda la app
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDragonBallDatabase(@ApplicationContext context: Context): DragonBallDatabase {
        return Room.databaseBuilder(
            context,
            DragonBallDatabase::class.java,
            "dragonball_database"  // ← Nombre del archivo en disco
        ).build()
    }

    @Provides
    @Singleton
    fun providePersonajeDao(database: DragonBallDatabase): PersonajeDao {
        // Cuando alguien pida PokemonDao, usa este métod
        // Hilt automáticamente pasa la BD que creó arriba
        return database.getPersonajeDao()
    }

    @Provides
    @Singleton
    fun providePlanetaDao(database: DragonBallDatabase): PlanetaDao{
        return database.getPlanetaDao()
    }
}