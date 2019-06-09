package com.sample.wireviewer.poko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class RelatedTopic : RealmObject() {
    @SerializedName("Icon")
    @Expose
    private var icon: Icon? = null
    @SerializedName("FirstURL")
    @Expose
    private var firstURL: String? = null
    @SerializedName("Result")
    @Expose
    private var result: String? = null
    @SerializedName("Text")
    @Expose
    private var text: String? = null

    private var name: String? = null
    private var description: String? = null


    fun getIcon(): Icon? {
        return icon
    }

    fun setIcon(icon: Icon) {
        this.icon = icon
    }

    fun getFirstURL(): String? {
        return firstURL
    }

    fun setFirstURL(firstURL: String) {
        this.firstURL = firstURL
    }

    fun getResult(): String? {
        return result
    }

    fun setResult(result: String) {
        this.result = result
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    fun gettName(): String? {
        return name

    }

    fun setName(name: String) {
        var name = name
        val separated = name.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        name = separated[0].trim { it <= ' ' }
        this.name = name
    }

    fun setDescription(description: String) {
        var description = description
        val separated = description.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        description = separated[separated.size.minus(1)].trim { it <= ' ' }
        this.description = description
    }

    fun getDescription(): String? {
        return this.description
    }
}