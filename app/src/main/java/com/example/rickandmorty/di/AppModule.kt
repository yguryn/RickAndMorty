package com.example.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.dataBase.DB
import com.example.rickandmorty.API.API
import com.example.rickandmorty.API.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): API =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DB =
        Room.databaseBuilder(app, DB::class.java, "database").allowMainThreadQueries()
            .build()
}