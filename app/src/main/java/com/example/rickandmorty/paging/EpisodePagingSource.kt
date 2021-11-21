package com.example.rickandmorty.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.dataBase.EpisodeDAO
import com.example.rickandmorty.API.API
import com.example.rickandmorty.models.Episode

class EpisodePagingSource(private val api: API, private val dao: EpisodeDAO) :
    PagingSource<Int, Episode>() {
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return null
    }

    companion object {
        var currentPage = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            currentPage = params.key ?: 1
            val response = api.getAllEpisodes(currentPage)
            val data = response.body()?.results ?: emptyList()
            Log.d("BBB", "$data")
            val responseData = mutableListOf<Episode>()
            dao.insertEpisodes(data)
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