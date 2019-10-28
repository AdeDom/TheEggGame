package com.adedom.theegggame.model

import android.os.Parcel
import android.os.Parcelable

data class SingleItem(
    val itemId: Int,
    val latitude: Double,
    val longitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(itemId)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleItem> {
        override fun createFromParcel(parcel: Parcel): SingleItem {
            return SingleItem(parcel)
        }

        override fun newArray(size: Int): Array<SingleItem?> {
            return arrayOfNulls(size)
        }
    }
}