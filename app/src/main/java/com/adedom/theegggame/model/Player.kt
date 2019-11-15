package com.adedom.theegggame.model

import android.os.Parcel
import android.os.Parcelable

data class Player(
    val playerId: String = "",
    val user: String = "",
    val name: String = "",
    val image: String = "empty",
    val level: Int = 1,
    val statusId: String = "player"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(playerId)
        parcel.writeString(user)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeInt(level)
        parcel.writeString(statusId)
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