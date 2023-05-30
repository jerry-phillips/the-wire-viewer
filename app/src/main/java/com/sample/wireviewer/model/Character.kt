package com.sample.wireviewer.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    @SerialName("Icon") val icon: Icon? = null,
    @SerialName("Text") val text: String? = null
) {
    companion object {
        fun getCharacterName(text: String?): String? {
            val separated =
                text?.split("-".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
            return separated?.get(0)?.trim { it <= ' ' }
        }

        fun getCharacterDescription(text: String?): String? {
            val separated =
                text?.split("-".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
            return separated?.get(separated.size.minus(1))?.trim { it <= ' ' }
        }
    }
}
