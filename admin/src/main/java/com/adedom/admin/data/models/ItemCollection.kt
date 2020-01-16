package com.adedom.admin.data.models

import com.adedom.library.data.*
import com.google.gson.annotations.SerializedName

data class ItemCollection(
    @SerializedName(KEY_VALUES1) val name: String,
    @SerializedName(KEY_VALUES2) val image: String,
    @SerializedName(KEY_VALUES3) val collectionId: String,
    @SerializedName(KEY_VALUES4) val playerId: String,
    @SerializedName(KEY_VALUES5) val itemId: Int,
    @SerializedName(KEY_VALUES6) val qty: Int,
    @SerializedName(KEY_VALUES7) val latitude: Double,
    @SerializedName(KEY_VALUES8) val longitude: Double,
    @SerializedName(KEY_VALUES9) val date: String,
    @SerializedName(KEY_VALUES10) val time: String
)