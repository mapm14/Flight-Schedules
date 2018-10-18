package com.manuelperera.flightsschedules.data.net

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.manuelperera.flightsschedules.BuildConfig.CLIENT_ID
import com.manuelperera.flightsschedules.BuildConfig.CLIENT_SECRET
import com.manuelperera.flightsschedules.BuildConfig.DEBUG
import com.manuelperera.flightsschedules.domain.service.ContextDataService
import com.manuelperera.flightsschedules.infrastructure.di.component.DaggerAppComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import javax.inject.Inject

class BaseHttpClient @Inject constructor(
        private val contextDataService: ContextDataService
) {

    fun getClient(requireAuthorization: Boolean = true): OkHttpClient {
        val httpBuilder = OkHttpClient()
                .newBuilder()
                .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = if (DEBUG) BODY else NONE
                        }
                )

        if (requireAuthorization) {
            httpBuilder.addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                if (contextDataService.getAccessToken().isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer ${contextDataService.getAccessToken()}")
                }
                chain.proceed(requestBuilder.build())
            }.authenticator { _, response ->
                val requestBuilder = response.request().newBuilder()

                if (response.code() == 401) {
                    val loginRepository = DaggerAppComponent.create().provideLoginRepository()
                    val either = loginRepository.login(CLIENT_ID, CLIENT_SECRET).blockingFirst()
                    when (either) {
                        is Right -> {
                            contextDataService.saveAccessToken(either.b)
                            requestBuilder.addHeader("Authorization", "Bearer ${either.b}")
                        }
                        is Left -> {
                            contextDataService.saveAccessToken("")
                            return@authenticator null
                        }
                    }
                }

                requestBuilder.build()
            }
        }

        return httpBuilder.build()
    }

}