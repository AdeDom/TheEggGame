package com.adedom.teg.data.di

import com.adedom.teg.data.db.AppDatabase
import com.adedom.teg.data.network.source.DataSourceProvider
import com.adedom.teg.data.network.source.TegDataSource
import com.adedom.teg.data.network.source.TegDataSourceImpl
import com.adedom.teg.data.network.websocket.TegWebSocket
import com.adedom.teg.data.repository.DefaultTegAuthRepositoryImpl
import com.adedom.teg.domain.repository.DefaultTegRepository
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.util.*
import org.koin.dsl.module

@KtorExperimentalAPI
private val dataModule = module {

    single { AppDatabase(get()) }

    single {
        HttpClient(OkHttp) {
            install(WebSockets)

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
        }
    }
    single { TegWebSocket(get(), get()) }

    single { DataSourceProvider(get(), get()) }

    single<TegDataSource> { TegDataSourceImpl(get(), get()) }

    single<DefaultTegRepository> { DefaultTegAuthRepositoryImpl(get()) }

}

@KtorExperimentalAPI
val getDataAndroidModule = dataModule
