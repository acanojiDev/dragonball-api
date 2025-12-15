package com.example.dragonballapi.data.model

data class Personaje(
    val id: Long,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String
)