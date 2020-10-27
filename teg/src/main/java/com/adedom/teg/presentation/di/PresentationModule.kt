package com.adedom.teg.presentation.di

import com.adedom.teg.presentation.backpack.BackpackViewModel
import com.adedom.teg.presentation.changeimage.ChangeImageViewModel
import com.adedom.teg.presentation.changepassword.ChangePasswordViewModel
import com.adedom.teg.presentation.changeprofile.ChangeProfileViewModel
import com.adedom.teg.presentation.imageprofile.ImageProfileViewModel
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.presentation.mission.MissionViewModel
import com.adedom.teg.presentation.playerprofile.PlayerProfileViewModel
import com.adedom.teg.presentation.rank.RankViewModel
import com.adedom.teg.presentation.room.RoomViewModel
import com.adedom.teg.presentation.roominfo.RoomInfoViewModel
import com.adedom.teg.presentation.setting.SettingViewModel
import com.adedom.teg.presentation.signin.SignInViewModel
import com.adedom.teg.presentation.signup.SignUpViewModel
import com.adedom.teg.presentation.single.SingleViewModel
import com.adedom.teg.presentation.splashscreen.SplashScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
private val presentationModule = module {

    viewModel { SignInViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ChangePasswordViewModel(get()) }
    viewModel { ChangeProfileViewModel(get(), get()) }
    viewModel { ChangeImageViewModel(get(), get()) }
    viewModel { ImageProfileViewModel(get(), get()) }
    viewModel { RankViewModel(get()) }
    viewModel { BackpackViewModel(get(), get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { SingleViewModel(get(), get()) }
    viewModel { MissionViewModel(get(), get()) }
    viewModel { RoomInfoViewModel(get()) }
    viewModel { PlayerProfileViewModel(get()) }
    viewModel { RoomViewModel(get()) }

}

@FlowPreview
@ExperimentalCoroutinesApi
val getPresentationModule = presentationModule
