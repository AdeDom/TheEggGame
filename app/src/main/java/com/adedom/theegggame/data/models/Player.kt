package com.adedom.theegggame.data.models

import com.adedom.library.util.*
import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName(KEY_VALUES1) val playerId: String? = null,
    @SerializedName(KEY_VALUES2) val username: String? = null,
    @SerializedName(KEY_VALUES3) val name: String? = null,
    @SerializedName(KEY_VALUES4) val image: String? = null,
    @SerializedName(KEY_VALUES5) val level: Int? = null,
    @SerializedName(KEY_VALUES6) val state: String? = null,
    @SerializedName(KEY_VALUES7) val gender: String? = null
)
