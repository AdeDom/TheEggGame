package com.adedom.theegggame.model

import android.os.Parcel
import android.os.Parcelable

data class PlayerItem(
    val id: String,
    val user: String,
    val password: String,
    val name: String,
    val image: String,
    val level: Int,
    val statusId: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(user)
        parcel.writeString(password)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeInt(level)
        parcel.writeString(statusId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerItem> {
        override fun createFromParcel(parcel: Parcel): PlayerItem {
            return PlayerItem(parcel)
        }

        override fun newArray(size: Int): Array<PlayerItem?> {
            return arrayOfNulls(size)
        }
    }
}