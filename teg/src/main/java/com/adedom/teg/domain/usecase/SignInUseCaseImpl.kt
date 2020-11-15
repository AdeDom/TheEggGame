package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.SignInRequest
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.presentation.usercase.SignInUseCase
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.util.TegConstant

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
                    preferenceConfig.signOut = false

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
            username.length < TegConstant.MIN_USERNAME -> ValidateSignIn.USERNAME_INCORRECT
            signIn.password == null -> ValidateSignIn.VALIDATE_ERROR
            password.isBlank() -> ValidateSignIn.PASSWORD_EMPTY
            password.length < TegConstant.MIN_PASSWORD -> ValidateSignIn.PASSWORD_INCORRECT
            else -> ValidateSignIn.VALIDATE_SUCCESS
        }
    }

    override fun isValidateUsername(username: String): Boolean {
        return username.isNotBlank() && username.length >= TegConstant.MIN_USERNAME
    }

    override fun isValidatePassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= TegConstant.MIN_PASSWORD
    }

}
