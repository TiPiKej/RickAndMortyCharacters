package com.kumor.rickandmortycharacters

import com.kumor.rickandmortycharacters.repository.Character

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class UiStateSimple(
    val data: List<Character>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
