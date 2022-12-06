package com.sample.wireviewer.characterlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    characterListRepository: CharacterListRepository
) : ViewModel() {

    val characters = MutableLiveData<List<Character>?>()
    val queriedCharacters = MutableLiveData<List<Character>>()
    val error = MutableLiveData<Resource.Error>()
    init {
        viewModelScope.launch {
            val resource = characterListRepository.getCharacters()
            if (resource is Resource.Success) {
                characters.value = resource.data
            } else {
                error.value = resource as Resource.Error
            }
        }
    }



    fun queryCharacters(query: String) {
        val tempResults = mutableListOf<Character>()
            if (characters.value != null) {
                for (character in characters.value!!) {
                    if (character.text?.contains(query, true) as Boolean) {
                        tempResults.add(character)
                    }
                }
                queriedCharacters.value = tempResults
            }

    }
}
