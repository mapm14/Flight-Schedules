package com.manuelperera.flightsschedules.domain.usecase.login

import arrow.core.Either
import arrow.core.None
import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.extensions.subObs
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.domain.usecase.base.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
        private val loginRepository: LoginRepository,
        private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : UseCase<String, None> {

    override fun invoke(params: None): Observable<Either<Failure, String>> =
            loginRepository
                    .login()
                    .flatMap { either ->
                        var token = ""
                        either.check({ token = it }, { token = "" })
                        saveAccessTokenUseCase(token)
                    }
                    .subObs()

}