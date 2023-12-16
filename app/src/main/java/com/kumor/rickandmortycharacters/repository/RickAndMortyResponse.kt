package com.kumor.rickandmortycharacters.repository

data class RickAndMortyResponse (
    val count: Int,
    val pages: Int,
    val results: List<Character>
)

data class Character (
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String
)