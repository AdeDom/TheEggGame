package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.models.request.SignInRequest
import com.adedom.teg.models.response.SignInResponse

interface SignInUseCase {

    suspend fun callSignIn(signIn: SignInRequest): Resource<SignInResponse>

    fun validateSignIn(signIn: SignInRequest): ValidateSignIn

    fun validateUsername(username: String): Boolean

    fun validatePassword(password: String): Boolean

}
