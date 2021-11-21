package com.example.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    val created: String,
    val dimension: String,
    @PrimaryKey val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)
