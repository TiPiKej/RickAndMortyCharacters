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
}