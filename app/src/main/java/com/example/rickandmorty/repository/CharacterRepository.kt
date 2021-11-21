package com.example.rickandmorty.repository

import androidx.room.withTransaction
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.util.networkBoundResource
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: API,
    private val db: DB,
) {
    private val characterDao = db.getCharacterDao()

    fun getCharacters() = networkBoundResource(
        query = {
            characterDao.getAllCharacters()
        },
        fetch = {
            api.getAllCharacters(10).body()!!.results
        },
        saveFetchResult = { characters ->
            db.withTransaction {
                characterDao.deleteAllCharacters()
                characterDao.insertCharacters(characters)
            }
        }
    )
}