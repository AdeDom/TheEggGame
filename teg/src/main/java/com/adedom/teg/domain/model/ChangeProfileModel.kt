package com.adedom.teg.domain.model

import java.util.*

data class ChangeProfileModel(
    val name: String? = null,
    val gender: String? = null,
    val birthDateString: String? = null,
    val birthDateCalendar: Calendar? = null,
)
