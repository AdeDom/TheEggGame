package com.adedom.teg.data.network.source

import com.adedom.teg.BuildConfig
import com.adedom.teg.data.network.api.TegApi
import com.adedom.teg.data.network.websocket.TegWebSocket
import com.adedom.teg.models.request.RefreshTokenRequest
import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.util.TegConstant
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@KtorExperimentalAPI
class DataSourceProvider(private val sessionManagerService: SessionManagerService) {

    fun getDataSource(): TegApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(TegConstant.BASE_URL)
        }.build()

        return retrofit.create(TegApi::class.java)
    }

    fun getTegDataSource(): TegApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addInterceptor { chain ->
                var response: Response = addHeaderToProceedRequest(chain)

                if (response.code == 401 || response.code == 403) {
                    callRefreshToken()
                    response = addHeaderToProceedRequest(chain)
                }

                response
            }

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(TegConstant.BASE_URL)
        }.build()

        return retrofit.create(TegApi::class.java)
    }

    fun getWebSocketDataSource(): TegWebSocket {
        val client = HttpClient(OkHttp) {
            install(WebSockets)

            request {
                header(TegConstant.ACCESS_TOKEN, sessionManagerService.accessToken)
            }
        }
        return TegWebSocket(client)
    }

    private fun callRefreshToken() {
        try {
            val request = RefreshTokenRequest(sessionManagerService.refreshToken)
            val response = runBlocking { getDataSource().callRefreshToken(request) }
            if (response.success) {
                val accessToken = response.token?.accessToken.orEmpty()
                val refreshToken = response.token?.refreshToken.orEmpty()
                sessionManagerService.accessToken = accessToken
                sessionManagerService.refreshToken = refreshToken
            }
        } catch (e: HttpException) {
            sessionManagerService.accessToken = ""
            sessionManagerService.refreshToken = ""
        }
    }

    private fun addHeaderToProceedRequest(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${sessionManagerService.accessToken}")
            .build()

        return chain.proceed(request)
    }

}
