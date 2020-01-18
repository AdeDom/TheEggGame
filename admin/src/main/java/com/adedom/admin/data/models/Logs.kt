package com.adedom.admin.data.models

import com.adedom.library.util.*
import com.google.gson.annotations.SerializedName

data class Logs(
    @SerializedName(KEY_VALUES1) val name: String,
    @SerializedName(KEY_VALUES2) val image: String,
    @SerializedName(KEY_VALUES3) val dateIn: String,
    @SerializedName(KEY_VALUES4) val timeIn: String,
    @SerializedName(KEY_VALUES5) val dateOut: String,
    @SerializedName(KEY_VALUES6) val timeOut: String
)