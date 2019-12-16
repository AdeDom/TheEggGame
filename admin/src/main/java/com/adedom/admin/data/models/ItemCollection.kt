package com.adedom.admin.data.models

import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class ItemCollection(
    @SerializedName(VALUES1) val name: String,
    @SerializedName(VALUES2) val image: String,
    @SerializedName(VALUES3) val collectionId: String,
    @SerializedName(VALUES4) val playerId: String,
    @SerializedName(VALUES5) val itemId: Int,
    @SerializedName(VALUES6) val qty: Int,
    @SerializedName(VALUES7) val latitude: Double,
    @SerializedName(VALUES8) val longitude: Double,
    @SerializedName(VALUES9) val date: String,
    @SerializedName(VALUES10) val time: String
)