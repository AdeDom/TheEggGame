package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RoomInfo(
    @SerializedName("values1") val room_no: String? = null,
    @SerializedName("values2") val latitude: Double? = null,
    @SerializedName("values3") val longitude: Double? = null,
    @SerializedName("values4") val team: String? = null,
    @SerializedName("values5") val status: String? = null,
    @SerializedName("values6") val playerId: String? = null,
    @SerializedName("values7") val name: String? = null,
    @SerializedName("values8") val image: String? = null,
    @SerializedName("values9") val level: Int? = null,
    @SerializedName("values10") val state: String? = null
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