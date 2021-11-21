package com.example.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    val episode: String,
    val air_date: String,
    @PrimaryKey val id: Int,
    val name: String,
    val characters: List<String>,
    val created: String,
)


