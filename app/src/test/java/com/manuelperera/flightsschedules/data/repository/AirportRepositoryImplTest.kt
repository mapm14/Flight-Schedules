package com.manuelperera.flightsschedules.data.repository

import com.manuelperera.flightsschedules.data.net.AirportApiClient
import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.AirportApi
import com.manuelperera.flightsschedules.data.repository.implementation.api.AirportRepositoryImpl
import com.manuelperera.flightsschedules.data.repository.model.getAirportResponse
import com.manuelperera.flightsschedules.extensions.assertGeneralsError
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
import com.manuelperera.flightsschedules.extensions.getObResultError
import com.manuelperera.flightsschedules.extensions.getObResultSuccess
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AirportRepositoryImplTest {

    private lateinit var airportRepositoryImpl: AirportRepositoryImpl

    @Mock
    private lateinit var airportApiClient: AirportApiClient
    @Mock
    private lateinit var airportApi: AirportApi

    @Before
    fun setUp() {
        whenever(airportApiClient.api).then { airportApi }

        airportRepositoryImpl = AirportRepositoryImpl(airportApiClient)
    }

    @Test
    fun `get airports should return airport list`() {
        whenever(airportApi.getAirports(
                anyInt(),
                anyInt(),
                anyString(),
                anyString()
        )).doReturn(getObResultSuccess(getAirportResponse()))

        val testObserver = airportRepositoryImpl.getAirports(
                0,
                0
        ).test()

        testObserver.assertGeneralsSuccess { it.isNotEmpty() }
    }

    @Test
    fun `get airports should return failure error`() {
        whenever(airportApi.getAirports(
                anyInt(),
                anyInt(),
                anyString(),
                anyString()
        )).doReturn(getObResultError())

        val testObserver = airportRepositoryImpl.getAirports(
                0,
                0
        ).test()

        testObserver.assertGeneralsError()
    }

}