package com.example.rickandmorty.repository

import androidx.room.withTransaction
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.util.networkBoundResource
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val api: API,
    private val db: DB,
) {
    private val episodeDao = db.getEpisodeDao()

    fun getEpisodes() = networkBoundResource(
        query = {
            episodeDao.getAllEpisodes()
        },
        fetch = {
            api.getAllEpisodes(1).body()!!.results
        },
        saveFetchResult = { episodes ->
            db.withTransaction {
                episodeDao.deleteAllEpisodes()
                episodeDao.insertEpisodes(episodes)
            }
        }
    )
}