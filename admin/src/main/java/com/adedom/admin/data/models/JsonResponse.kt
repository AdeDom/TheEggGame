package com.adedom.admin.data.models

import com.adedom.utility.RESULT
import com.google.gson.annotations.SerializedName

data class JsonResponse(@SerializedName(RESULT) val result: String? = null)