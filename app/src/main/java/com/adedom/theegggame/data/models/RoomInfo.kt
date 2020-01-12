package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class RoomInfo(
    @SerializedName(VALUES1) val room_no: String? = null,
    @SerializedName(VALUES2) val latitude: Double? = null,
    @SerializedName(VALUES3) val longitude: Double? = null,
    @SerializedName(VALUES4) val team: String? = null,
    @SerializedName(VALUES5) val status: String? = null,
    @SerializedName(VALUES6) val playerId: String? = null,
    @SerializedName(VALUES7) val name: String? = null,
    @SerializedName(VALUES8) val image: String? = null,
    @SerializedName(VALUES9) val level: Int? = null,
    @SerializedName(VALUES10) val state: String? = null,
    @SerializedName(VALUES11) val gender: String? = null
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