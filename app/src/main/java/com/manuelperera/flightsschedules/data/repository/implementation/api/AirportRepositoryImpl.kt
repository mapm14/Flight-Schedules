package com.manuelperera.flightsschedules.data.repository.implementation.api

import arrow.core.Either
import com.manuelperera.flightsschedules.data.net.AirportApiClient
import com.manuelperera.flightsschedules.data.repository.base.BaseRepository
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.repository.AirportRepository
import io.reactivex.Observable
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(
        private val airportApiClient: AirportApiClient
) : BaseRepository(), AirportRepository {

    override fun getAirports(limit: Int, offset: Int): Observable<Either<Failure, List<Airport>>> =
            modifyObservable(airportApiClient.api.getAirports(limit, offset))

}