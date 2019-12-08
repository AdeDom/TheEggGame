package com.adedom.utility

import com.google.gson.annotations.SerializedName

data class Single(
    val itemId: Int,
    val latitude: Double,
    val longitude: Double
)

data class Score(
    @SerializedName(VALUES1) val teamA: Int,
    @SerializedName(VALUES2) val teamB: Int
)