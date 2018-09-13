package com.manuelperera.flightsschedules.domain.usecase

import com.manuelperera.flightsschedules.domain.model.getScheduleList
import com.manuelperera.flightsschedules.domain.repository.ScheduleRepository
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase.Params
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSchedulesUseCaseTest {

    private lateinit var getSchedulesUseCase: GetSchedulesUseCase

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRule()
    @Mock
    private lateinit var scheduleRepository: ScheduleRepository

    @Before
    fun setUp() {
        getSchedulesUseCase = GetSchedulesUseCase(scheduleRepository)
    }

    @Test
    fun `invoke should return flight list`() {
        whenever(scheduleRepository.getSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt()
        )).doReturn(getObEitherSuccess(getScheduleList()))

        val testObserver = getSchedulesUseCase(
                Params("", "", 0, 0, "")
        ).test().await()

        testObserver.assertGeneralsSuccess { it.isNotEmpty() }
    }

    @Test
    fun `invoke should return failure`() {
        whenever(scheduleRepository.getSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt()
        )).doReturn(getObEitherError())

        val testObserver = getSchedulesUseCase(
                Params("", "", 0, 0, "")
        ).test().await()

        testObserver.assertGeneralsError()
    }

}