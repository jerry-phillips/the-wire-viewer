package com.sample.wireviewer.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.wireviewer.services.DuckDuckGoService
import com.sample.wireviewer.poko.Character
import com.sample.wireviewer.poko.RequestData
import com.sample.wireviewer.services.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.duckduckgo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create<DuckDuckGoService>(DuckDuckGoService::class.java)

    fun getCharacters():LiveData<Resource<List<Character>>>{
        val data = MutableLiveData<Resource<List<Character>>>()
        service.getWireCharacters(CharacterListViewModel.WIREFRAMEQUERY, CharacterListViewModel.DATAFORMAT).enqueue(object :
            Callback<RequestData>{
            override fun onFailure(call: Call<RequestData>, t: Throwable) {
                data.value = Resource.Error(t.localizedMessage)
            }

            override fun onResponse(call: Call<RequestData>, response: Response<RequestData>) {
                val requestData = response.body()
                data.value = Resource.Success(requestData?.getRelatedTopics() as List<Character>)
            }


        })
        return data
    }
}