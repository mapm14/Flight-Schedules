package com.manuelperera.flightsschedules.domain.usecase

import com.manuelperera.flightsschedules.domain.model.getAirportList
import com.manuelperera.flightsschedules.domain.repository.AirportRepository
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase.Params
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRule
import com.manuelperera.flightsschedules.extensions.assertGeneralsError
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAirportsUseCaseTest {

    private lateinit var getAirportsUseCase: GetAirportsUseCase

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRule()
    @Mock
    private lateinit var airportRepository: AirportRepository

    @Before
    fun setUp() {
        getAirportsUseCase = GetAirportsUseCase(airportRepository)
    }

    @Test
    fun `invoke should return airports list`() {
        whenever(airportRepository.getAirports(
                anyInt(),
                anyInt()
        )).doReturn(getObEitherSuccess(getAirportList()))

        val testObserver = getAirportsUseCase(
                Params(0, 0)
        ).test().await()

        testObserver.assertGeneralsSuccess { it.isNotEmpty() }
    }

    @Test
    fun `invoke should return failure`() {
        whenever(airportRepository.getAirports(
                anyInt(),
                anyInt()
        )).doReturn(getObEitherError())

        val testObserver = getAirportsUseCase(
                Params(0, 0)
        ).test().await()

        testObserver.assertGeneralsError()
    }

}