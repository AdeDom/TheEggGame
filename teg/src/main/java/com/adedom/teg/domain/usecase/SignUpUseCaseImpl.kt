package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.SignUpModel
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.SignUpRequest
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.presentation.usercase.SignUpUseCase
import com.adedom.teg.sharedpreference.service.SessionManagerService
import java.util.*

class SignUpUseCaseImpl(
    private val repository: DefaultTegRepository,
    private val sessionManagerService: SessionManagerService,
) : SignUpUseCase {

    override suspend fun callSignUp(signUp: SignUpModel): Resource<SignInResponse> {
        val birthDate = getStringBirthDate(signUp.birthDate)
        val request = SignUpRequest(
            username = signUp.username,
            password = signUp.password,
            name = signUp.name,
            gender = signUp.gender,
            birthDate = birthDate,
        )
        val resource = repository.callSignUp(request)

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

    override fun getStringBirthDate(birthDate: Calendar?): String {
        val year = birthDate?.get(Calendar.YEAR)
        val month = birthDate?.get(Calendar.MONTH)
        val dayOfMonth = birthDate?.get(Calendar.DAY_OF_MONTH)
        return "$dayOfMonth/${month?.plus(1)}/$year"
    }

}
