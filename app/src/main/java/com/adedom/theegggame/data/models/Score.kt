package com.adedom.theegggame.data.models

import com.adedom.library.util.KEY_VALUES1
import com.adedom.library.util.KEY_VALUES2
import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName(KEY_VALUES1) val teamA: Int,
    @SerializedName(KEY_VALUES2) val teamB: Int
)