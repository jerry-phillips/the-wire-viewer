package com.sample.wireviewer.characterlist

import com.sample.wireviewer.R
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.services.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val WIREFRAMEQUERY = "the wire characters"
const val DATAFORMAT = "json"
class CharacterListRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.duckduckgo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(DuckDuckGoService::class.java)

    suspend fun getCharacters():Resource<List<Character>>{
        var data = service.getWireCharacters(WIREFRAMEQUERY, DATAFORMAT)

        return if (data.isSuccessful) {
            Resource.Success(data.body()?.getRelatedTopics() as List<Character>)
        } else {
            Resource.Error(R.string.failure)
        }
    }
}
