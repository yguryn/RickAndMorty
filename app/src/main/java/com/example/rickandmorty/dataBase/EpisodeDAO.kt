package com.example.rickandmorty.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.models.Episode
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Query("select * from episodes")
    fun getAllEpisodes(): Flow<List<Episode>>

    @Query("select * from episodes where id == :id")
    fun getEpisodeById(id: Int): Episode

    @Query("select exists(select * from episodes where id == :id)")
    fun isRowExist(id: Int): Boolean

    @Query("delete from episodes")
    suspend fun deleteAllEpisodes()
}