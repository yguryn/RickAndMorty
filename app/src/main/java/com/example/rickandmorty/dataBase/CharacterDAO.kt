package com.example.rickandmorty.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.models.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("select * from characters")
    fun getAllCharacters(): Flow<List<Character>>

    @Query("select * from characters where id == :id")
    fun getCharacterById(id: Int): Character

    @Query("select exists(select * from characters where id == :id)")
    fun isRowExist(id: Int): Boolean

    @Query("delete from characters")
    suspend fun deleteAllCharacters()
}