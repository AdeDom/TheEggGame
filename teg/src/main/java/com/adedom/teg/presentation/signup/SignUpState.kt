package com.adedom.teg.presentation.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val loading: Boolean = false,
)
