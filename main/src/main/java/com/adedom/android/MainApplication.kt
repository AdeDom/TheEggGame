package com.adedom.android

import android.app.Application
import com.adedom.teg.data.di.getDataAndroidModule
import com.adedom.teg.domain.di.getDomainModule
import com.adedom.teg.presentation.di.getPresentationModule
import com.adedom.teg.sharedpreference.di.getSharedPreferenceModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            koin.loadModules(
                listOf(
                    getPresentationModule,
                    getDomainModule,
                    getDataAndroidModule,
                    getSharedPreferenceModule
                )
            )
            koin.createRootScope()
        }

        Stetho.initializeWithDefaults(this)
    }
}
