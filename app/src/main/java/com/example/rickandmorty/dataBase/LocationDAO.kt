package com.example.rickandmorty.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)

    @Query("select * from locations")
    fun getAllLocations(): Flow<List<Location>>

    @Query("select * from locations where id == :id")
    fun getLocationById(id: Int): Location

    @Query("delete from locations")
    suspend fun deleteAllLocations()
}