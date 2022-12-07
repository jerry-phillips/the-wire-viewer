package com.sample.wireviewer.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("Icon")
    val icon: Icon? = null,
    @SerializedName("Text")
    val text: String? = null
)  {

    fun getCharacterName(): String {
        val separated = this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return separated[0].trim { it <= ' ' }
    }

    fun getCharacterDescription(): String {
        val separated = this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return separated[separated.size.minus(1)].trim { it <= ' ' }
    }
}
