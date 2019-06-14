package com.sample.wireviewer.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.wireviewer.interfaces.DuckDuckGoService
import com.sample.wireviewer.poko.Character
import com.sample.wireviewer.poko.RequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListViewModel: ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.duckduckgo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create<DuckDuckGoService>(DuckDuckGoService::class.java)

    private val characters: MutableLiveData<List<Character>> by lazy {
        MutableLiveData<List<Character>>().also {
            loadCharacters()
        }
    }

    fun getCharacters(): LiveData<List<Character>> {
        return characters
    }

    private fun loadCharacters() {
        service.getWireCharacters(WIREFRAMEQUERY, DATAFORMAT).enqueue(object :
            Callback<RequestData> {
            override fun onResponse(call: Call<RequestData>, response: Response<RequestData>) {
                val requestData = response.body()
                for (character in requestData?.getRelatedTopics()!!) {
                    character.setName(character.getText() as String)
                    character.setDescription(character.getText() as String)
                }
                characters.value = requestData.getRelatedTopics()
            }

            override fun onFailure(call: Call<RequestData>, t: Throwable) {
            }
        })
    }

     fun queryCharacters(query: String):LiveData<List<Character>> {
        val queryResults = MutableLiveData<List<Character>>()
         val tempResults = mutableListOf<Character>()
        val iterator = characters.value?.listIterator()
        if (iterator != null) {
            for (character in iterator){
                if (character.getText()?.contains(query, true) as Boolean){
                    tempResults.add(character)
                }
            }
            queryResults.value = tempResults
        }
         return queryResults
    }


    companion object {
        //This demo's requirements can allow for these parameters to be static
        const val WIREFRAMEQUERY = "the wire characters"
        const val DATAFORMAT = "json"
    }
}