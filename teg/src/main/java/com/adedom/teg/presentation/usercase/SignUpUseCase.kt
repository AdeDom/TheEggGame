package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.SignUpRequest
import com.adedom.teg.models.response.SignInResponse

interface SignUpUseCase {

    suspend fun callSignUp(signUp: SignUpRequest): Resource<SignInResponse>

}
