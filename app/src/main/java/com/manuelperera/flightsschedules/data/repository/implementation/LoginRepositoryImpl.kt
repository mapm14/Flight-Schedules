package com.manuelperera.flightsschedules.data.repository.implementation

import arrow.core.Either
import com.manuelperera.flightsschedules.data.repository.base.BaseRepository
import com.manuelperera.flightsschedules.data.repository.client.LoginApiClient
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
        private val loginApiClient: LoginApiClient
) : BaseRepository(), LoginRepository {

    override fun login(clientId: String, clientSecret: String): Observable<Either<Failure, String>> =
            modifyObservable(loginApiClient.api.login(clientId, clientSecret))

}