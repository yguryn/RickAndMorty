package com.example.rickandmorty.repository

import androidx.room.withTransaction
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.util.networkBoundResource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val api: API,
    private val db: DB,
) {
    private val locationDao = db.getLocationDao()

    fun getLocations() = networkBoundResource(
        query = {
            locationDao.getAllLocations()
        },
        fetch = {
            api.getAllLocations(1).body()!!.results
        },
        saveFetchResult = { locations ->
            db.withTransaction {
                locationDao.deleteAllLocations()
                locationDao.insertLocations(locations)
            }
        }
    )
}