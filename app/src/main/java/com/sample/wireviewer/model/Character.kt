package com.sample.wireviewer.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    @SerialName("Icon") val icon: Icon? = null,
    @SerialName("Text") val text: String? = null
) {

    fun getCharacterName(): String {
        val separated =
            this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return separated[0].trim { it <= ' ' }
    }

    fun getCharacterDescription(): String {
        val separated =
            this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return separated[separated.size.minus(1)].trim { it <= ' ' }
    }
}
