package com.example.dragonballapi.data.remote.model

data class PersonajeListRemote(
    val items: List<PersonajeListItemRemote>
)

data class PersonajeListItemRemote(
    val id: Long,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String
)

data class PersonajeRemote(
    val id: Long,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String
)