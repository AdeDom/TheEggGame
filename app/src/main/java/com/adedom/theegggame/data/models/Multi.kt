package com.adedom.theegggame.data.models

import com.adedom.library.data.*
import com.google.gson.annotations.SerializedName

data class Multi(
    @SerializedName(KEY_VALUES1) val multi_id: String,
    @SerializedName(KEY_VALUES2) val room_no: String,
    @SerializedName(KEY_VALUES3) val latitude: Double,
    @SerializedName(KEY_VALUES4) val longitude: Double,
    @SerializedName(KEY_VALUES5) val status: String
)