package com.manuelperera.flightsschedules.domain.usecase.airport

import com.manuelperera.flightsschedules.domain.extensions.subObs
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.repository.AirportRepository
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase.Params
import com.manuelperera.flightsschedules.domain.usecase.base.UseCase
import javax.inject.Inject

class GetAirportsUseCase @Inject constructor(
        private val airportRepository: AirportRepository
) : UseCase<List<Airport>, Params> {

    override fun invoke(params: Params) =
            airportRepository.getAirports(params.limit, params.offset).subObs()

    class Params(val limit: Int = 20,
                 val offset: Int)

}