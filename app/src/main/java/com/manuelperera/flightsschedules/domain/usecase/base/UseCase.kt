package com.manuelperera.flightsschedules.domain.usecase.base

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.base.Failure
import io.reactivex.Observable

interface UseCase<Type, in Params> {

    operator fun invoke(params: Params): Observable<Either<Failure, Type>>

}