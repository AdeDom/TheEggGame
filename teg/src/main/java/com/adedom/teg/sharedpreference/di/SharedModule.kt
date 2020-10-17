package com.adedom.teg.sharedpreference.di

import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.sharedpreference.service.SessionManagerServiceImpl
import org.koin.dsl.module

private val sharedPreferenceModule = module {

    single<SessionManagerService> { SessionManagerServiceImpl(get()) }

}

val getSharedPreferenceModule = sharedPreferenceModule
