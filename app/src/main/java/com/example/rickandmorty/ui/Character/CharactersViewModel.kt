package com.example.rickandmorty.ui.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Episode
import com.example.rickandmorty.paging.CharacterPagingSource
import com.example.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(repository: CharacterRepository, api: API, db: DB) : ViewModel() {

    val characters = repository.getCharacters().asLiveData()
    val characterDao = db.getCharacterDao()
    val episodeDao = db.getEpisodeDao()

    val listData = Pager(PagingConfig(pageSize = 1)) {
        CharacterPagingSource(api, characterDao)
    }.flow.cachedIn(viewModelScope)

    fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    fun getEpisodeById(id: List<Int>): MutableList<Episode> {
        val characters: MutableList<Episode> = mutableListOf()
        for (i in id) {
            if(episodeDao.isRowExist(i)) {
                characters.add(episodeDao.getEpisodeById(i))
            }
        }
        return characters
    }

}
