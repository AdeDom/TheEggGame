package com.adedom.theegggame.models

import android.os.Parcel
import android.os.Parcelable

data class RoomItem(
    val id: String,
    val no: String,
    val name: String,
    val password: String,
    val people: String,
    val status_id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(no)
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(people)
        parcel.writeString(status_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomItem> {
        override fun createFromParcel(parcel: Parcel): RoomItem {
            return RoomItem(parcel)
        }

        override fun newArray(size: Int): Array<RoomItem?> {
            return arrayOfNulls(size)
        }
    }
}