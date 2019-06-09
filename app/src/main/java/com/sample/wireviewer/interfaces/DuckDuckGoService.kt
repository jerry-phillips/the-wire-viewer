package com.sample.wireviewer.interfaces

import com.sample.wireviewer.poko.RequestData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckDuckGoService {

        @GET("./")
        fun getWireCharacters(
            @Query("q") query: String,
            @Query("format") format: String
        ): Call<RequestData>


}