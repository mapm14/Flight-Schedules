package com.manuelperera.flightsschedules.domain.usecase.login

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.service.ContextDataService
import com.manuelperera.flightsschedules.domain.usecase.base.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
        private val contextDataService: ContextDataService
) : UseCase<String, String> {

    override fun invoke(params: String): Observable<Either<Failure, String>> =
            contextDataService.saveAccessToken(params)

}