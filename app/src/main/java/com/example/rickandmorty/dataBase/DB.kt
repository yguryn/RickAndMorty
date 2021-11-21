package com.example.rickandmorty.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.models.Character

import com.example.rickandmorty.models.Episode
import com.example.rickandmorty.models.EpisodeTypeConverter
import com.example.rickandmorty.models.Location

@Database(entities = [Character::class, Episode::class, Location::class], version = 1)
@TypeConverters(EpisodeTypeConverter::class)
abstract class DB : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDAO
    abstract fun getEpisodeDao(): EpisodeDAO
    abstract fun getLocationDao(): LocationDAO
}