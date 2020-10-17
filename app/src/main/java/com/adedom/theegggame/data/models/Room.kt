package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable

data class Room(
    val room_id: String? = null,
    val room_no: String? = null,
    val name: String? = null,
    val people: String? = null,
    val status: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(room_id)
        parcel.writeString(room_no)
        parcel.writeString(name)
        parcel.writeString(people)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}