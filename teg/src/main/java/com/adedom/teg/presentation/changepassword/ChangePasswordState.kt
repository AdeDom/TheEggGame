package com.adedom.teg.presentation.changepassword

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val reNewPassword: String = "",
    val isClickable: Boolean = true,
    val loading: Boolean = false,
)
