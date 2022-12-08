package com.sample.wireviewer.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    characterListRepository: CharacterListRepository,
    private val characterStorage: MutableList<Character>
) : ViewModel() {

    val characters = MutableStateFlow<DuckDuckGoResponse>(DuckDuckGoResponse.Empty)

    init {
        viewModelScope.launch {
            val resource = characterListRepository.getCharacters()
            if (resource is DuckDuckGoResponse.Success) {
                characters.value = resource
                characterStorage.addAll(resource.data)
            } else {
                characters.value = resource as DuckDuckGoResponse.Error
            }
        }
    }

    fun resetData(){
        characters.value = DuckDuckGoResponse.Success(characterStorage)
    }

    fun queryCharacters(query: String) {
        val tempResults = mutableListOf<Character>()
        for (character in characterStorage) {
            if (character.text?.contains(query, true) as Boolean) {
                tempResults.add(character)
            }
        }
        characters.value = DuckDuckGoResponse.Success(tempResults)
    }
}
