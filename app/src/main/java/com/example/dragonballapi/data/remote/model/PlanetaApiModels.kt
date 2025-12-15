package com.example.dragonballapi.data.remote.model

data class PlanetaListRemote(
    val results: List<PlanetaListItemRemote>
)

data class PlanetaListItemRemote(
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)

data class PlanetaRemote(
    val id: Long,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)