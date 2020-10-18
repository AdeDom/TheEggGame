package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.SignUpModel
import com.adedom.teg.models.response.SignInResponse
import java.util.*

interface SignUpUseCase {

    suspend fun callSignUp(signUp: SignUpModel): Resource<SignInResponse>

    fun getStringBirthDate(birthDate: Calendar?): String

}
