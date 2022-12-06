package com.sample.wireviewer.model

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("RelatedTopics")
    val characters: List<Character>? = null
)
