package com.example.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.dataBase.LocationDAO
import com.example.rickandmorty.API.API
import com.example.rickandmorty.models.Location

class LocationPagingSource(private val api: API, private val dao: LocationDAO) :
    PagingSource<Int, Location>() {
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return null
    }

    companion object {
        var currentPage = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        return try {
            currentPage = params.key ?: 1
            val response = api.getAllLocations(currentPage)
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<Location>()
            dao.insertLocations(data)
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