package com.adedom.teg.presentation.di

import com.adedom.teg.presentation.changeimage.ChangeImageViewModel
import com.adedom.teg.presentation.changepassword.ChangePasswordViewModel
import com.adedom.teg.presentation.changeprofile.ChangeProfileViewModel
import com.adedom.teg.presentation.imageprofile.ImageProfileViewModel
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.presentation.signin.SignInViewModel
import com.adedom.teg.presentation.signup.SignUpViewModel
import com.adedom.teg.presentation.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {

    viewModel { SignInViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ChangePasswordViewModel(get()) }
    viewModel { ChangeProfileViewModel(get(), get()) }
    viewModel { ChangeImageViewModel(get(), get()) }
    viewModel { ImageProfileViewModel(get(), get()) }

}

val getPresentationModule = presentationModule
