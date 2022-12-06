package com.sample.wireviewer.services

import com.sample.wireviewer.model.Character

sealed class Resource {
    class Success(val data: List<Character>) : Resource()
    class Error: Resource()
}