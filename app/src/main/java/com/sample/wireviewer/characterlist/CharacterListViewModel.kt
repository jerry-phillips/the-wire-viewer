package com.sample.wireviewer.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import kotlinx.coroutines.launch

class CharacterListViewModel(
    characterListRepository: CharacterListRepository = CharacterListRepository()
) : ViewModel() {

    val characters = MutableLiveData<List<Character>?>()

    init {
        viewModelScope.launch {
            characters.value = characterListRepository.getCharacters().data
        }
    }



    fun queryCharacters(query: String): LiveData<List<Character>> {
        val queryResults = MutableLiveData<List<Character>>()
        val tempResults = mutableListOf<Character>()
        val iterator = characters.value?.listIterator()
        if (iterator != null) {
            for (character in iterator) {
                if (character.text?.contains(query, true) as Boolean) {
                    tempResults.add(character)
                }
            }
            queryResults.value = tempResults
        }
        return queryResults
    }
}
