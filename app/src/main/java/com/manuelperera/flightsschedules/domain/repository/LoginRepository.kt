package com.manuelperera.flightsschedules.domain.repository

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.base.Failure
import io.reactivex.Observable

interface LoginRepository {

    fun login(clientId: String, clientSecret: String): Observable<Either<Failure, String>>

}