package com.manuelperera.flightsschedules.domain.repository

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.Failure
import io.reactivex.Observable

interface AirportRepository {

    fun getAirports(limit: Int, offset: Int): Observable<Either<Failure, List<Airport>>>

}