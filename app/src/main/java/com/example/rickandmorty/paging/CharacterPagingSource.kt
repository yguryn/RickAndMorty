package com.example.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.dataBase.CharacterDAO
import com.example.rickandmorty.API.API
import com.example.rickandmorty.models.Character


class CharacterPagingSource(private val api: API, private val dao: CharacterDAO) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }

    companion object {
        var currentPage = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            currentPage = params.key ?: 1
            val response = api.getAllCharacters(currentPage)
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<Character>()
            dao.insertCharacters(data)
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}