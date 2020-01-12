package com.adedom.admin.data.models

import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName(VALUES1) val playerId: String? = null,
    @SerializedName(VALUES2) val username: String? = null,
    @SerializedName(VALUES3) val password: String? = null,
    @SerializedName(VALUES4) val name: String? = null,
    @SerializedName(VALUES5) val image: String? = null,
    @SerializedName(VALUES6) val level: Int? = null,
    @SerializedName(VALUES7) val state: String? = null,
    @SerializedName(VALUES8) val date: String? = null,
    @SerializedName(VALUES9) val time: String? = null,
    @SerializedName(VALUES10) val gender: String? = null
)