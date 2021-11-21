package com.example.rickandmorty.ui.Episode

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
import com.example.rickandmorty.paging.EpisodePagingSource
import com.example.rickandmorty.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel
@Inject constructor(repository: EpisodeRepository, api: API, db: DB) : ViewModel() {

    val episodes = repository.getEpisodes().asLiveData()
    private val episodeDAO = db.getEpisodeDao()
    private val characterDAO = db.getCharacterDao()

    val listData = Pager(PagingConfig(pageSize = 1)) {
        EpisodePagingSource(api, episodeDAO)
    }.flow.cachedIn(viewModelScope)

    fun getEpisodeById(id: Int): Episode {
        return episodeDAO.getEpisodeById(id)
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