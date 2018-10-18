package com.manuelperera.flightsschedules.data.repository

import com.manuelperera.flightsschedules.data.net.ScheduleApiClient
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.ScheduleApi
import com.manuelperera.flightsschedules.data.repository.implementation.api.ScheduleRepositoryImpl
import com.manuelperera.flightsschedules.data.repository.model.getScheduleResponse
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
class ScheduleRepositoryImplTest {

    private lateinit var scheduleRepositoryImpl: ScheduleRepositoryImpl

    @Mock
    private lateinit var scheduleApiClient: ScheduleApiClient
    @Mock
    private lateinit var scheduleApi: ScheduleApi

    @Before
    fun setUp() {
        whenever(scheduleApiClient.api).then { scheduleApi }

        scheduleRepositoryImpl = ScheduleRepositoryImpl(scheduleApiClient)
    }

    @Test
    fun `get schedules should return flight list`() {
        whenever(scheduleApi.getSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt(),
                anyString()
        )).doReturn(getObResultSuccess(getScheduleResponse()))

        val testObserver = scheduleRepositoryImpl.getSchedules(
                "",
                "",
                "",
                0,
                0
        ).test()

        testObserver.assertGeneralsSuccess { it.isNotEmpty() }
    }

    @Test
    fun `get schedules should return failure error`() {
        whenever(scheduleApi.getSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt(),
                anyString()
        )).doReturn(getObResultError())

        val testObserver = scheduleRepositoryImpl.getSchedules(
                "",
                "",
                "",
                0,
                0
        ).test()

        testObserver.assertGeneralsError()
    }

}