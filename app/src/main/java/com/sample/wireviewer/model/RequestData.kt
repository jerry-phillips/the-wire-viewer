package com.sample.wireviewer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestData {

    @SerializedName("RelatedTopics")
    @Expose
    private var characters: List<Character>? = null


    fun getRelatedTopics(): List<Character>? {
        return characters
    }
}
