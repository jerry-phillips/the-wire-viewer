package com.sample.wireviewer.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Character() :Parcelable {
    @SerializedName("Icon")
    @Expose
    var icon: Icon? = null
    @SerializedName("FirstURL")
    @Expose
    var firstURL: String? = null
    @SerializedName("Result")
    @Expose
    var result: String? = null
    @SerializedName("Text")
    @Expose
    var text: String? = null

    var name: String? = null
    var description: String? = null

    constructor(parcel: Parcel) : this() {
        icon = parcel.readParcelable(Icon::class.java.classLoader)
        firstURL = parcel.readString()
        result = parcel.readString()
        text = parcel.readString()
        name = parcel.readString()
        description = parcel.readString()
    }

    fun getCharacterName(): String? {
        val separated = this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.name = separated[0].trim { it <= ' ' }
        return name

    }


    fun getCharacterDescription(): String? {
        val separated = this.text!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.description = separated[separated.size.minus(1)].trim { it <= ' ' }
        return description
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(icon, flags)
        parcel.writeString(firstURL)
        parcel.writeString(result)
        parcel.writeString(text)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}
