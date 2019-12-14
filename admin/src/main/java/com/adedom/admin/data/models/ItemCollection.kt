package com.adedom.admin.data.models

import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class ItemCollection(
    @SerializedName(VALUES1) val name: String,
    @SerializedName(VALUES2) val image: String,
    @SerializedName(VALUES3) val itemId: Int,
    @SerializedName(VALUES4) val qty: Int,
    @SerializedName(VALUES5) val latitude: Double,
    @SerializedName(VALUES6) val longitude: Double,
    @SerializedName(VALUES7) val date: String,
    @SerializedName(VALUES8) val time: String
)