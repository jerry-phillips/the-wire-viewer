package com.sample.wireviewer.ui

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoService
import javax.inject.Inject
import kotlin.jvm.Throws

private const val WIRE_QUERY = "the wire characters"
private const val DATA_FORMAT = "json"

class CharacterListRepository @Inject constructor(private val duckGoService: DuckDuckGoService) {

    @Throws(Exception::class)
    suspend fun getCharacters(): List<Character>? {
        val data = duckGoService.getWireCharacters(WIRE_QUERY, DATA_FORMAT)

        return if (data.isSuccessful) {
            data.body()?.characters
        } else {
            null
        }
    }
}
