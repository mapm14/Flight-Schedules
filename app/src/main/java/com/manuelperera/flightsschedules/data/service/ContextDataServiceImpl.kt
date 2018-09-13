package com.manuelperera.flightsschedules.data.service

import arrow.core.Either
import arrow.core.Right
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.service.ContextDataService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextDataServiceImpl @Inject constructor() : ContextDataService {

    private lateinit var accessToken: String

    override fun saveAccessToken(accessToken: String): Observable<Either<Failure, String>> = Observable.create {
        this.accessToken = accessToken
        it.onNext(Right(accessToken))
        it.onComplete()
    }

    override fun getAccessToken() = accessToken

}