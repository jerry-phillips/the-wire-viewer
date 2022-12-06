package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.services.Resource
import javax.inject.Inject

const val WIREFRAMEQUERY = "the wire characters"
const val DATAFORMAT = "json"

class CharacterListRepository @Inject constructor(private val duckGoService: DuckDuckGoService) {

    suspend fun getCharacters(): Resource {
        val data = duckGoService.getWireCharacters(WIREFRAMEQUERY, DATAFORMAT)

        return if (data.isSuccessful) {
            Resource.Success(data.body()?.getRelatedTopics() as List<Character>)
        } else {
            Resource.Error()
        }
    }
}
