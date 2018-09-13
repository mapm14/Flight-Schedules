package com.manuelperera.flightsschedules.domain.service

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.base.Failure
import io.reactivex.Observable

interface ContextDataService {

    fun saveAccessToken(accessToken: String): Observable<Either<Failure, String>>

    fun getAccessToken(): String

}