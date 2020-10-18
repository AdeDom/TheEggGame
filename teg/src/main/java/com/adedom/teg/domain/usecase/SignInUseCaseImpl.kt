package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Constant
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.SignInRequest
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.presentation.usercase.SignInUseCase
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import com.adedom.teg.sharedpreference.service.SessionManagerService

class SignInUseCaseImpl(
    private val repository: DefaultTegRepository,
    private val sessionManagerService: SessionManagerService,
    private val preferenceConfig: PreferenceConfig,
) : SignInUseCase {

    override suspend fun callSignIn(signIn: SignInRequest): Resource<SignInResponse> {
        val resource = repository.callSignIn(signIn)

        when (resource) {
            is Resource.Success -> {
                val response = resource.data
                if (response.success) {
                    val accessToken = response.token?.accessToken.orEmpty()
                    val refreshToken = response.token?.refreshToken.orEmpty()
                    sessionManagerService.accessToken = accessToken
                    sessionManagerService.refreshToken = refreshToken

                    preferenceConfig.username = signIn.username.orEmpty()
                }
            }
        }

        return resource
    }

    override fun validateSignIn(signIn: SignInRequest): ValidateSignIn {
        val username = signIn.username.orEmpty()
        val password = signIn.password.orEmpty()
        return when {
            signIn.username == null -> ValidateSignIn.VALIDATE_ERROR
            username.isBlank() -> ValidateSignIn.USERNAME_EMPTY
            username.length < Constant.USERNAME_LENGTH -> ValidateSignIn.USERNAME_INCORRECT
            signIn.password == null -> ValidateSignIn.VALIDATE_ERROR
            password.isBlank() -> ValidateSignIn.PASSWORD_EMPTY
            password.length < Constant.PASSWORD_LENGTH -> ValidateSignIn.PASSWORD_INCORRECT
            else -> ValidateSignIn.VALIDATE_SUCCESS
        }
    }

    override fun validateUsername(username: String): Boolean {
        return username.isNotBlank() && username.length >= Constant.USERNAME_LENGTH
    }

    override fun validatePassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= Constant.PASSWORD_LENGTH
    }

    override fun getConfigUsername(): String {
        return preferenceConfig.username
    }

}
