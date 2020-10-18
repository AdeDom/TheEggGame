package com.adedom.teg.domain.model

import java.util.*

data class SignUpModel(
    val username: String? = null,
    val password: String? = null,
    val rePassword: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val birthDate: Calendar? = null,
)
