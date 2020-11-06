package com.adedom.teg.data.di

import com.adedom.teg.data.db.AppDatabase
import com.adedom.teg.data.network.source.DataSourceProvider
import com.adedom.teg.data.network.source.TegDataSource
import com.adedom.teg.data.network.source.TegDataSourceImpl
import com.adedom.teg.data.repository.DefaultTegAuthRepositoryImpl
import com.adedom.teg.domain.repository.DefaultTegRepository
import io.ktor.util.*
import org.koin.dsl.module

@KtorExperimentalAPI
private val dataModule = module {

    single { AppDatabase(get()) }

    single { DataSourceProvider(get()) }

    single<TegDataSource> { TegDataSourceImpl(get(), get()) }

    single<DefaultTegRepository> { DefaultTegAuthRepositoryImpl(get()) }

}

@KtorExperimentalAPI
val getDataAndroidModule = dataModule
