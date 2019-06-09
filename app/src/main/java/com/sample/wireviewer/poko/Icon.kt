package com.sample.wireviewer.poko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Icon: RealmObject() {

    @SerializedName("URL")
    @Expose
    private var uRL: String? = null
    @SerializedName("Height")
    @Expose
    private var height: String? = null
    @SerializedName("Width")
    @Expose
    private var width: String? = null

    fun getURL(): String? {
        return uRL
    }

    fun setURL(uRL: String) {
        this.uRL = uRL
    }

    fun getHeight(): String? {
        return height
    }

    fun setHeight(height: String) {
        this.height = height
    }

    fun getWidth(): String? {
        return width
    }

    fun setWidth(width: String) {
        this.width = width
    }

}