package com.sample.wireviewer.model

import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("URL") var url: String? = null
)
