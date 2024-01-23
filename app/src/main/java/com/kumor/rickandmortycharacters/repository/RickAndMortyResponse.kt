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
    val image: String,
    val location: Location
)

data class Location (
    val name: String,
    val url: String
)
