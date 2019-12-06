package com.adedom.theegggame.data.models

import com.adedom.utility.RESULT
import com.google.gson.annotations.SerializedName

data class JsonResponse(@SerializedName(RESULT) val result: String? = null)