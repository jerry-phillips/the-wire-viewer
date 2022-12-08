package com.sample.wireviewer.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    characterListRepository: CharacterListRepository,
    private val characterStorage: MutableList<Character>
) : ViewModel() {

    val characters = MutableStateFlow<CharacterData>(CharacterData.Empty)

    init {
        viewModelScope.launch {
            val characterData = characterListRepository.getCharacters()
            if (characterData is CharacterData.Success) {
                characters.value = characterData
                characterStorage.addAll(characterData.data)
            } else {
                characters.value = characterData as CharacterData.Error
            }
        }
    }

    fun resetData(){
        characters.value = CharacterData.Success(characterStorage)
    }

    fun queryCharacters(query: String) {
        val tempResults = mutableListOf<Character>()
        for (character in characterStorage) {
            if (character.text?.contains(query, true) as Boolean) {
                tempResults.add(character)
            }
        }
        characters.value = CharacterData.Success(tempResults)
    }
}
