package com.manuelperera.flightsschedules.data.service

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.manuelperera.flightsschedules.domain.model.base.CallInfo
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.base.Failure.Error
import com.manuelperera.flightsschedules.domain.service.ContextDataService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextDataServiceImpl @Inject constructor() : ContextDataService {

    private var accessToken = ""

    override fun saveAccessToken(accessToken: String): Observable<Either<Failure, String>> = Observable.create {
        this.accessToken = accessToken
        if (accessToken.isNotEmpty()) {
            it.onNext(Right(accessToken))
        } else {
            it.onNext(Left(Error(CallInfo(400, "No access token"))))
        }
        it.onComplete()
    }

    override fun getAccessToken() = accessToken

}