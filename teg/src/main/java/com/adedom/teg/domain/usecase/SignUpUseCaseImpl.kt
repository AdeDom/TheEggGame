package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.SignUpUseCase
import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.models.request.SignUpRequest
import com.adedom.teg.models.response.SignInResponse

class SignUpUseCaseImpl(
    private val repository: DefaultTegRepository,
    private val sessionManagerService: SessionManagerService,
) : SignUpUseCase {

    override suspend fun callSignUp(signUp: SignUpRequest): Resource<SignInResponse> {
        val resource = repository.callSignUp(signUp)

        when (resource) {
            is Resource.Success -> {
                val response = resource.data
                if (response.success) {
                    val accessToken = response.token?.accessToken.orEmpty()
                    val refreshToken = response.token?.refreshToken.orEmpty()
                    sessionManagerService.accessToken = accessToken
                    sessionManagerService.refreshToken = refreshToken
                }
            }
        }

        return resource
    }

}
