package com.adedom.teg.presentation.changeprofile

import java.util.*

data class ChangeProfileState(
    val name: String = "",
    val gender: String = "",
    val birthDateString: String = "",
    val birthDateCalendar: Calendar? = null,
    val loading: Boolean = false,
)