package com.adedom.admin.data.models

import com.adedom.utility.data.*
import com.google.gson.annotations.SerializedName

data class Logs(
    @SerializedName(VALUES1) val name: String,
    @SerializedName(VALUES2) val image: String,
    @SerializedName(VALUES3) val dateIn: String,
    @SerializedName(VALUES4) val timeIn: String,
    @SerializedName(VALUES5) val dateOut: String,
    @SerializedName(VALUES6) val timeOut: String
)