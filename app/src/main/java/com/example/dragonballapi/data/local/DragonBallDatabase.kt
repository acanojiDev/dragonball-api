package com.example.dragonballapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dragonballapi.data.local.personaje.PersonajeDao
import com.example.dragonballapi.data.local.personaje.PersonajeEntity
import com.example.dragonballapi.data.local.planeta.PlanetaDao
import com.example.dragonballapi.data.local.planeta.PlanetaEntity

@Database(
    entities = [PersonajeEntity::class, PlanetaEntity::class],
    version = 1
)
abstract class DragonBallDatabase: RoomDatabase(){
    abstract fun getPersonajeDao(): PersonajeDao
    abstract fun getPlanetaDao(): PlanetaDao
}