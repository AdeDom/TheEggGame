package com.adedom.theegggame.models

import android.os.Parcel
import android.os.Parcelable

data class RoomInfoItem(
    val room_no: String,
    val playerId: String,
    val name: String,
    val image: String,
    val level: Int,
    val latitude: Double,
    val longitude: Double,
    val team: String,
    val status_id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(room_no)
        parcel.writeString(playerId)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeInt(level)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(team)
        parcel.writeString(status_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomInfoItem> {
        override fun createFromParcel(parcel: Parcel): RoomInfoItem {
            return RoomInfoItem(parcel)
        }

        override fun newArray(size: Int): Array<RoomInfoItem?> {
            return arrayOfNulls(size)
        }
    }
}