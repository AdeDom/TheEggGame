package com.adedom.theegggame.data.models

import com.adedom.utility.*
import com.google.gson.annotations.SerializedName

data class Multi(
    @SerializedName(VALUES1) val multi_id: String,
    @SerializedName(VALUES2) val room_no: String,
    @SerializedName(VALUES3) val latitude: Double,
    @SerializedName(VALUES4) val longitude: Double,
    @SerializedName(VALUES5) val player_id: String,
    @SerializedName(VALUES6) val status: String
)