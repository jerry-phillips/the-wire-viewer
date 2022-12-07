package com.sample.wireviewer.services

import com.sample.wireviewer.model.Character

sealed class DuckDuckGoResponse {
    class Success(val data: List<Character>) : DuckDuckGoResponse()
    object Error : DuckDuckGoResponse()
}