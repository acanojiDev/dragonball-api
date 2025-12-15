package com.example.dragonballapi.data.local.planeta

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.dragonballapi.data.local.planeta.PlanetaEntity

@Dao
interface PlanetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planeta: PlanetaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planetaList: List<PlanetaEntity>)

    @Delete
    suspend fun delete(planeta: PlanetaEntity): Int

    @Query("SELECT * FROM planeta")
    suspend fun getAll(): List<PlanetaEntity>

    @Query("SELECT * FROM planeta")
    fun observeAll(): Flow<List<PlanetaEntity>>

    @Query("SELECT * FROM planeta WHERE id = :id")
    suspend fun readPlanetaById(id: Long): PlanetaEntity?

    @Query("DELETE FROM planeta")
    suspend fun deleteAll()
}