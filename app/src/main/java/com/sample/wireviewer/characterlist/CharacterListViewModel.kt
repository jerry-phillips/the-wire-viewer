package com.sample.wireviewer.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterListRepository: CharacterListRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<CharacterData>(CharacterData.Empty)
    val characters: StateFlow<CharacterData> = _characters

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
                val characters = characterListRepository.getCharacters()

                if (!characters.isNullOrEmpty()) {
                    _characters.value = CharacterData.Success(characters)
                } else {
                    _characters.value = CharacterData.Error
                }
        }
    }

    fun queryCharacters(query: String) {
        val tempResults = mutableListOf<Character>()
        val characterList = (characters.value as CharacterData.Success).data
        for (character in characterList) {
            if (character.text?.contains(query, true) as Boolean) {
                tempResults.add(character)
            }
        }
        _characters.value = CharacterData.Success(tempResults)
    }
}
