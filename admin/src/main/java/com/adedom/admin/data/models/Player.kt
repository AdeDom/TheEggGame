package com.adedom.admin.data.models

import com.adedom.library.data.*
import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName(KEY_VALUES1) val playerId: String? = null,
    @SerializedName(KEY_VALUES2) val username: String? = null,
    @SerializedName(KEY_VALUES3) val password: String? = null,
    @SerializedName(KEY_VALUES4) val name: String? = null,
    @SerializedName(KEY_VALUES5) val image: String? = null,
    @SerializedName(KEY_VALUES6) val level: Int? = null,
    @SerializedName(KEY_VALUES7) val state: String? = null,
    @SerializedName(KEY_VALUES8) val date: String? = null,
    @SerializedName(KEY_VALUES9) val time: String? = null,
    @SerializedName(KEY_VALUES10) val gender: String? = null
)