package com.example.dragonballapi.data.local.planeta

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dragonballapi.data.model.Planeta

@Entity("planeta")
data class PlanetaEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)

fun Planeta.toEntity(): PlanetaEntity {
    return PlanetaEntity(
        id = this.id,
        name = this.name,
        isDestroyed =  this.isDestroyed,
        description = this.description,
        image = this.image
    )
}

fun List<Planeta>.toEntity():List<PlanetaEntity> = this.map(Planeta::toEntity)

fun PlanetaEntity.toModel(): Planeta {
    return Planeta(
        id = this.id,
        name = this.name,
        isDestroyed =  this.isDestroyed,
        description = this.description,
        image = this.image
    )
}

fun List<PlanetaEntity>.toModel(): List<Planeta> {
    return this.map(PlanetaEntity::toModel)
}