package com.adedom.teg.sharedpreference.di

import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.sharedpreference.service.SessionManagerServiceImpl
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import com.adedom.teg.sharedpreference.service.PreferenceConfigImpl
import org.koin.dsl.module

private val sharedPreferenceModule = module {

    single<SessionManagerService> { SessionManagerServiceImpl(get()) }

    single<PreferenceConfig> { PreferenceConfigImpl(get()) }

}

val getSharedPreferenceModule = sharedPreferenceModule
