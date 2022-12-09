package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoService
import javax.inject.Inject

const val WIREQUERY = "the wire characters"
const val DATAFORMAT = "json"

class CharacterListRepository @Inject constructor(private val duckGoService: DuckDuckGoService) {

    suspend fun getCharacters(): List<Character>? {
        val data = duckGoService.getWireCharacters(WIREQUERY, DATAFORMAT)

        return if (data.isSuccessful) {
            data.body()?.characters
        } else {
            null
        }
    }
}
