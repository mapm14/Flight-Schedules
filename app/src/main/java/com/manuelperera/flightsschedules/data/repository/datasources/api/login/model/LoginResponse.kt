package com.manuelperera.flightsschedules.data.repository.datasources.api.login.model

import com.google.gson.annotations.SerializedName
import com.manuelperera.flightsschedules.domain.model.base.ResponseObject

class LoginResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String = "",
        @SerializedName("expires_in") val expiresIn: Int = 0
) : ResponseObject<String> {

    override fun toAppDomain(): String = accessToken

}