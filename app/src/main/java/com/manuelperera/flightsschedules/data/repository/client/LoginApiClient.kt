package com.manuelperera.flightsschedules.data.repository.client

import com.manuelperera.flightsschedules.BuildConfig.BASE_URL
import com.manuelperera.flightsschedules.data.repository.datasources.api.login.LoginApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class LoginApiClient @Inject constructor(baseHttpClient: BaseHttpClient) {

    val api: LoginApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(baseHttpClient.getClient(false))
            .build()
            .create(LoginApi::class.java)

}