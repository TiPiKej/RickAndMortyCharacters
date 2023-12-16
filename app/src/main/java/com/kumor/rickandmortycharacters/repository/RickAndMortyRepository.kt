package com.kumor.rickandmortycharacters.repository

import retrofit2.Response

class RickAndMortyRepository {
    suspend fun getRickAndMortyResponse(): Response<RickAndMortyResponse> =
        RickAndMortyService.rickAndMortyService.getRickAndMortyCharacters()
}