package com.adedom.theegggame.data.models

import com.adedom.library.util.KEY_VALUES1
import com.adedom.library.util.KEY_VALUES2
import com.adedom.library.util.KEY_VALUES3
import com.adedom.library.util.KEY_VALUES4
import com.google.gson.annotations.SerializedName

data class Backpack(
    @SerializedName(KEY_VALUES1) val egg: Int = 0,
    @SerializedName(KEY_VALUES2) val eggI: Int = 0,
    @SerializedName(KEY_VALUES3) val eggII: Int = 0,
    @SerializedName(KEY_VALUES4) val eggIII: Int = 0
)