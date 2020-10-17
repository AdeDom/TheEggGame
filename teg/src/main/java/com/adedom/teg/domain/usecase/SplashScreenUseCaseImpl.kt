package com.adedom.teg.domain.usecase

import com.adedom.teg.presentation.usercase.SplashScreenUseCase
import com.adedom.teg.sharedpreference.service.SessionManagerService

class SplashScreenUseCaseImpl(
    private val sessionManagerService: SessionManagerService,
) : SplashScreenUseCase {

    override fun isValidateSignIn(): Boolean {
        return !sessionManagerService.accessToken.isBlank()
    }

}
