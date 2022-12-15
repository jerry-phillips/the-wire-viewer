package com.sample.wireviewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.AppDispatchers
import com.sample.wireviewer.services.CharacterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterListRepository: CharacterListRepository,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    private val _characters = MutableStateFlow<CharacterData>(CharacterData.Empty)
    val characters: StateFlow<CharacterData> = _characters

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
                _characters.value = withContext(appDispatchers.IO) {
                    try {
                        val characters = characterListRepository.getCharacters()
                        if (!characters.isNullOrEmpty()) {
                             CharacterData.Success(characters)
                        } else {
                             CharacterData.NoData
                        }
                    } catch (e: Exception) {
                        CharacterData.Error
                }
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
