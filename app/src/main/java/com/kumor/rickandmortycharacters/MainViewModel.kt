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

    val liveDataCharacters = MutableLiveData<List<Character>>()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = rickAndMortyRepository.getRickAndMortyResponse()
                Log.d("MainViewModel", "request response code: ${request.code()}")

                val characters = request.body()?.results ?: emptyList()
                liveDataCharacters.postValue(characters)
            } catch (e: Exception) {
                Log.d("MainViewModel", "request failed, exception", e)
            }
        }
    }
}