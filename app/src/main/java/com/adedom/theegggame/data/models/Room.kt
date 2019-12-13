package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName(VALUES1) val room_id: String? = null,
    @SerializedName(VALUES2) val room_no: String? = null,
    @SerializedName(VALUES3) val name: String? = null,
    @SerializedName(VALUES4) val people: String? = null,
    @SerializedName(VALUES5) val status: String? = null
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