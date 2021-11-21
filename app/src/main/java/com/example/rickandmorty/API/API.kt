package com.example.rickandmorty.API

import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.models.EpisodesResponse
import com.example.rickandmorty.models.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): Response<CharactersResponse>

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") page: Int
    ): Response<EpisodesResponse>

    @GET("location")
    suspend fun getAllLocations(
        @Query("page") page: Int
    ): Response<LocationResponse>
}