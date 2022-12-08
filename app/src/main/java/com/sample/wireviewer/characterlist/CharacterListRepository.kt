package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import com.sample.wireviewer.services.DuckDuckGoService
import javax.inject.Inject

const val WIREQUERY = "the wire characters"
const val DATAFORMAT = "json"

class CharacterListRepository @Inject constructor(private val duckGoService: DuckDuckGoService) {

    suspend fun getCharacters(): CharacterData {
        val data = duckGoService.getWireCharacters(WIREQUERY, DATAFORMAT)

        return if (data.isSuccessful) {
            CharacterData.Success(data.body()?.characters as List<Character>)
        } else {
            CharacterData.Error
        }
    }
}
