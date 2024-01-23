package com.kumor.rickandmortycharacters.repository

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("/api/character")
    suspend fun getRickAndMortyCharacters(): Response<RickAndMortyResponse>

    @GET("/api/character/{id}")
    suspend fun getRickAndMortyCharacter(@Path("id") id: Int): Response<Character>

    companion object {
        private const val  BASE_URL = "https://rickandmortyapi.com/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val rickAndMortyService: RickAndMortyService by lazy {
            retrofit.create(RickAndMortyService::class.java)
        }
    }
}