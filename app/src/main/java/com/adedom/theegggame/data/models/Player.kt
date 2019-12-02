package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("values1") val playerId: String? = null,
    @SerializedName("values2") val username: String? = null,
    @SerializedName("values3") val name: String? = null,
    @SerializedName("values4") val image: String? = null,
    @SerializedName("values5") val level: Int? = null,
    @SerializedName("values6") val status: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(playerId)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeValue(level)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}