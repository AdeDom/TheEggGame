package com.adedom.theegggame.model

import android.os.Parcel
import android.os.Parcelable

data class ChatItem(
    val playerId: String,
    val time: String,
    val message: String,
    val image: String,
    val type: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(playerId)
        parcel.writeString(time)
        parcel.writeString(message)
        parcel.writeString(image)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatItem> {
        override fun createFromParcel(parcel: Parcel): ChatItem {
            return ChatItem(parcel)
        }

        override fun newArray(size: Int): Array<ChatItem?> {
            return arrayOfNulls(size)
        }
    }
}