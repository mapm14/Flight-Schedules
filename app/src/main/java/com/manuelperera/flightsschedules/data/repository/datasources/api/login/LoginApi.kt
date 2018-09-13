package com.manuelperera.flightsschedules.data.repository.datasources.api.login

import com.manuelperera.flightsschedules.BuildConfig.CLIENT_ID
import com.manuelperera.flightsschedules.BuildConfig.CLIENT_SECRET
import com.manuelperera.flightsschedules.data.repository.datasources.api.login.model.LoginResponse
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("oauth/token")
    fun login(
            @Field("client_id") clientId: String = CLIENT_ID,
            @Field("client_secret") clientSecret: String = CLIENT_SECRET,
            @Field("grant_type") grantType: String = "client_credentials"
    ): Observable<Result<LoginResponse>>

}