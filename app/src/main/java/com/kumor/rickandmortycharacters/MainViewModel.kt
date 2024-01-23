package com.kumor.rickandmortycharacters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumor.rickandmortycharacters.repository.Character
import com.kumor.rickandmortycharacters.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val rickAndMortyRepository = RickAndMortyRepository()

    val liveDataCharacters = MutableLiveData<UiState<List<Character>>>()
    val liveDataCharacter = MutableLiveData<UiState<Character>>()

    fun getData() {
        liveDataCharacters.postValue(UiState(isLoading = true))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = rickAndMortyRepository.getRickAndMortyResponse()
                Log.d("MainViewModel", "request response code: ${request.code()}")
                if (request.isSuccessful) {
                    val characters = request.body()?.results ?: emptyList()
                    liveDataCharacters.postValue(UiState(data = characters))
                } else {
                    liveDataCharacters.postValue(UiState(error = "${request.code()}"))
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "request failed, exception", e)
            }
        }
    }

    fun getCharacterData(id: Int) {
        liveDataCharacter.postValue(UiState(isLoading = true))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = rickAndMortyRepository.getRickAndMortyCharacter(id)
                Log.d("MainViewModel", "request response code: ${request.code()}")
                if (request.isSuccessful) {
                    val character = request.body()
                    liveDataCharacter.postValue(UiState(data = character))
                } else {
                    liveDataCharacter.postValue(UiState(error = "${request.code()}"))
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "request failed, exception", e)
            }
        }
    }
}