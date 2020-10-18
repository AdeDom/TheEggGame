package com.adedom.teg.domain.di

import com.adedom.teg.domain.usecase.*
import com.adedom.teg.presentation.usercase.*
import org.koin.dsl.module

private val domainModule = module {

    single<SignInUseCase> { SignInUseCaseImpl(get(), get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get(), get()) }
    single<SplashScreenUseCase> { SplashScreenUseCaseImpl(get()) }
    single<MainUseCase> { MainUseCaseImpl(get(), get()) }
    single<ChangePasswordUseCase> { ChangePasswordUseCaseImpl(get(), get()) }
    single<ChangeProfileUseCase> { ChangeProfileUseCaseImpl(get()) }
    single<ChangeImageUseCase> { ChangeImageUseCaseImpl(get()) }

}

val getDomainModule = domainModule
