package com.adedom.teg.presentation.splashscreen

import com.adedom.teg.presentation.usercase.SplashScreenUseCase
import com.adedom.teg.base.BaseViewModel

class SplashScreenViewModel(
    private val userCase: SplashScreenUseCase,
) : BaseViewModel<SplashScreenState>(SplashScreenState) {

    fun initialize() = userCase.isValidateSignIn()

}
