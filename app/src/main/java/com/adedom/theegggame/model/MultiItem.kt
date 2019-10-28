package com.adedom.theegggame.model

import android.os.Parcel
import android.os.Parcelable

data class MultiItem(
    val id: String,
    val room_no: String,
    val latitude: Double,
    val longitude: Double,
    val player_id: String,
    val status_id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(room_no)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(player_id)
        parcel.writeString(status_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MultiItem> {
        override fun createFromParcel(parcel: Parcel): MultiItem {
            return MultiItem(parcel)
        }

        override fun newArray(size: Int): Array<MultiItem?> {
            return arrayOfNulls(size)
        }
    }
}