package com.adedom.teg.presentation.changeprofile

data class ChangeProfileState(
    val name: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val loading: Boolean = false,
)
