package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable

data class RoomInfo(
    val room_no: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val team: String? = null,
    val status: String? = null,
    val playerId: String? = null,
    val name: String? = null,
    val image: String? = null,
    val level: Int? = null,
    val state: String? = null,
    val gender: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(room_no)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(team)
        parcel.writeString(status)
        parcel.writeString(playerId)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeValue(level)
        parcel.writeString(state)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomInfo> {
        override fun createFromParcel(parcel: Parcel): RoomInfo {
            return RoomInfo(parcel)
        }

        override fun newArray(size: Int): Array<RoomInfo?> {
            return arrayOfNulls(size)
        }
    }
}