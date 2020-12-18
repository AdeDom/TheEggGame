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
    single<SingleUseCase> { SingleUseCaseImpl(get()) }
    single<SettingUseCase> { SettingUseCaseImpl(get(), get(), get()) }
    single<MissionUseCase> { MissionUseCaseImpl(get()) }
    single<MultiUseCase> { MultiUseCaseImpl(get()) }
    single<CreateRoomUseCase> { CreateRoomUseCaseImpl(get()) }
    single<RoomInfoUseCase> { RoomInfoUseCaseImpl(get()) }
    single<EndTegGameUseCase> { EndTegGameUseCaseImpl(get()) }
    single<BonusTegGameUseCase> { BonusTegGameUseCaseImpl(get()) }

}

val getDomainModule = domainModule
