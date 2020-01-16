package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.adedom.library.data.*
import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName(KEY_VALUES1) val playerId: String? = null,
    @SerializedName(KEY_VALUES2) val username: String? = null,
    @SerializedName(KEY_VALUES3) val name: String? = null,
    @SerializedName(KEY_VALUES4) val image: String? = null,
    @SerializedName(KEY_VALUES5) val level: Int? = null,
    @SerializedName(KEY_VALUES6) val state: String? = null,
    @SerializedName(KEY_VALUES7) val gender: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(playerId)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeValue(level)
        parcel.writeString(state)
        parcel.writeString(gender)
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