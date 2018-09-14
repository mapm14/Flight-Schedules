package com.manuelperera.flightsschedules.domain.usecase.login

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.extensions.subObs
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.domain.usecase.base.UseCase
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase.Params
import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
        private val loginRepository: LoginRepository,
        private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : UseCase<String, Params> {

    override fun invoke(params: Params): Observable<Either<Failure, String>> =
            loginRepository
                    .login(params.clientId, params.clientSecret)
                    .flatMap { either ->
                        var token = ""
                        either.check({ token = "" }, { token = it })
                        saveAccessTokenUseCase(token)
                    }
                    .subObs()

    class Params(
            val clientId: String,
            val clientSecret: String
    )

}