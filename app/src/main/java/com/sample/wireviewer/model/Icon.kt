package com.sample.wireviewer.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Icon() :Parcelable {

    @SerializedName("URL")
    var uRL: String? = null
    @SerializedName("Height")
    var height: String? = null
    @SerializedName("Width")
    var width: String? = null

    constructor(parcel: Parcel) : this() {
        uRL = parcel.readString()
        height = parcel.readString()
        width = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uRL)
        parcel.writeString(height)
        parcel.writeString(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Icon> {
        override fun createFromParcel(parcel: Parcel): Icon {
            return Icon(parcel)
        }

        override fun newArray(size: Int): Array<Icon?> {
            return arrayOfNulls(size)
        }
    }
}
