package com.sample.wireviewer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestData(
    @SerialName("RelatedTopics") val characters: List<Character>? = null
)
