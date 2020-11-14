package com.adedom.teg.presentation.signin

data class SignInState(
    val username: String = "",
    val password: String = "",
    val isValidUsername: Boolean = false,
    val isValidPassword: Boolean = false,
    val isClickable: Boolean = true,
    val loading: Boolean = false
)
