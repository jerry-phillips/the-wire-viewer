package com.sample.wireviewer.services

import com.sample.wireviewer.model.RequestData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
const val ENDPOINT = "https://api.duckduckgo.com/"
interface DuckDuckGoService {
        @GET("./")
        suspend fun getWireCharacters(
            @Query("q") query: String,
            @Query("format") format: String
        ): Response<RequestData>
}
