package com.sample.wireviewer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Icon(
    @SerialName("URL") val url: String? = null
)
