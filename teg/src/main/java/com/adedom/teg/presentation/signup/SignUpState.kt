package com.adedom.teg.presentation.signup

import java.util.*

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val rePassword: String = "",
    val name: String = "",
    val gender: String = "",
    val birthDateCalendar: Calendar? = null,
    val birthDateString: String = "",
    val loading: Boolean = false,
)
