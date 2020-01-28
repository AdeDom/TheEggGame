package com.adedom.theegggame.data.models

import android.os.Parcel
import android.os.Parcelable
import com.adedom.library.util.KEY_VALUES1
import com.adedom.library.util.KEY_VALUES2
import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName(KEY_VALUES1) val teamA: Int,
    @SerializedName(KEY_VALUES2) val teamB: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(teamA)
        parcel.writeInt(teamB)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Score> {
        override fun createFromParcel(parcel: Parcel): Score {
            return Score(parcel)
        }

        override fun newArray(size: Int): Array<Score?> {
            return arrayOfNulls(size)
        }
    }
}