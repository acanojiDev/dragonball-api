package com.example.dragonballapi.data.local.personaje

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dragonballapi.data.model.Personaje

@Entity("personaje")
data class PersonajeEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String
)

fun Personaje.toEntity(): PersonajeEntity {
    return PersonajeEntity(
        id = this.id,
        name = this.name,
        ki = this.ki,
        race = this.race,
        gender = this.gender,
        description = this.description,
        image = this.image
    )
}

fun List<Personaje>.toEntity():List<PersonajeEntity> = this.map(Personaje::toEntity)

fun PersonajeEntity.toModel(): Personaje {
    return Personaje(
        id = this.id,
        name = this.name,
        ki = this.ki,
        race = this.race,
        gender = this.gender,
        description = this.description,
        image = this.image
    )
}

fun List<PersonajeEntity>.toModel(): List<Personaje> {
    return this.map(PersonajeEntity::toModel)
}