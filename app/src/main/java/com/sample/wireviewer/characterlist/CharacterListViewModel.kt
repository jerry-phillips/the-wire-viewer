package com.sample.wireviewer.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.Resource

class CharacterListViewModel(
    characterListRepository: CharacterListRepository = CharacterListRepository()
) : ViewModel() {

    private val characters: LiveData<Resource<List<Character>>> =
        characterListRepository.getCharacters()

    fun getCharacters(): LiveData<Resource<List<Character>>> {
        return characters
    }


    fun queryCharacters(query: String): LiveData<List<Character>> {
        val queryResults = MutableLiveData<List<Character>>()
        val tempResults = mutableListOf<Character>()
        val iterator = characters.value?.data?.listIterator()
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
