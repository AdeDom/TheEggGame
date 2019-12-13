package com.adedom.utility.data

import com.google.gson.annotations.SerializedName

data class JsonResponse(@SerializedName(RESULT) val result: String? = null)