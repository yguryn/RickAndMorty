package com.example.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "characters")
data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

class EpisodeTypeConverter {
    @TypeConverter
    fun fromList(list: List<String?>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}