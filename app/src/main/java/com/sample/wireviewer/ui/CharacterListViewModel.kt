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
    private val appDispatchers: AppDispatchers,
    private val characterCache: MutableList<Character>
) : ViewModel() {

    private val _characters = MutableStateFlow<CharacterData>(CharacterData.Empty)
    val characters: StateFlow<CharacterData> = _characters

    init {
        fetchCharacters()
    }

    fun resetCharacterData(){
        _characters.value = CharacterData.Success(characterCache)
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _characters.value = withContext(appDispatchers.IO) {
                try {
                    val characters = characterListRepository.getCharacters()
                    if (!characters.isNullOrEmpty()) {
                        characterCache.addAll(characters)
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
        val tempResults = characterCache.filter { character ->
            character.text?.contains(query, true) as Boolean
        }
        _characters.value = CharacterData.Success(tempResults)
    }
}
