package com.sample.wireviewer.services

import com.sample.wireviewer.model.Character

sealed class CharacterData {
    class Success(val data: List<Character>) : CharacterData()
    object Error : CharacterData()
    object NoData: CharacterData()
    object Empty: CharacterData()
}