package com.example.rickandmorty.ui.Location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Location
import com.example.rickandmorty.paging.LocationPagingSource
import com.example.rickandmorty.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
@Inject
constructor(repository: LocationRepository, api: API, db: DB) : ViewModel() {

    val locations = repository.getLocations().asLiveData()
    private val locationDao = db.getLocationDao()
    private val characterDAO = db.getCharacterDao()

    val listData = Pager(PagingConfig(pageSize = 1)) {
        LocationPagingSource(api, locationDao)
    }.flow.cachedIn(viewModelScope)

    fun getLocationById(id: Int): Location {
        return locationDao.getLocationById(id)
    }

    fun getCharactersById(id: List<Int>): MutableList<Character> {
        val characters: MutableList<Character> = mutableListOf()
        for (i in id) {
            if (characterDAO.isRowExist(i)) {
                characters.add(characterDAO.getCharacterById(i))
            }
        }
        return characters
    }
}

