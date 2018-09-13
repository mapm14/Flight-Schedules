package com.manuelperera.flightsschedules.presentation

import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.getAirportList
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRule
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherNoMoreData
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingView
import com.manuelperera.flightsschedules.presentation.selectairport.AirportListPresenter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AirportsListPresenterTest {

    private lateinit var airportListPresenter: AirportListPresenter

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRule()
    @Mock
    private lateinit var getAirportsUseCase: GetAirportsUseCase
    @Mock
    private lateinit var airportView: PagingView<Airport>

    @Before
    fun setUp() {
        airportListPresenter = AirportListPresenter(getAirportsUseCase)
        airportListPresenter.init(airportView)
    }

    @Test
    fun `getAirportsPage should invoke onLoadPageSuccess`() {
        val scheduleList = getAirportList()
        val page = 1
        whenever(getAirportsUseCase(any())).doAnswer { getObEitherSuccess(scheduleList) }

        airportListPresenter.getAirportsPage(page)

        assert(airportListPresenter.compositeDisposable.size() == 1)
        verify(airportListPresenter.view)?.onLoadPageSuccess(scheduleList, false)
        verify(airportListPresenter.view, never())?.onLoadPageError(page == 1)
    }

    @Test
    fun `getAirportsPage fail with Failure-Error should invoke onLoadPageError`() {
        val page = 1
        whenever(getAirportsUseCase.invoke(any())).doAnswer { getObEitherError() }

        airportListPresenter.getAirportsPage(page)

        assert(airportListPresenter.compositeDisposable.size() == 1)
        verify(airportListPresenter.view)?.onLoadPageError(any())
        verify(airportListPresenter.view)?.showSnackbar("Error")
        verify(airportListPresenter.view, never())?.onLoadPageSuccess(any(), any())
    }

    @Test
    fun `getAirportsPage fail with Failure-NoMoreData should invoke onFinishList`() {
        val page = 1
        whenever(getAirportsUseCase.invoke(any())).doAnswer { getObEitherNoMoreData() }

        airportListPresenter.getAirportsPage(page)

        assert(airportListPresenter.compositeDisposable.size() == 1)
        verify(airportListPresenter.view)?.onFinishList()
        verify(airportListPresenter.view, never())?.showSnackbar("Error")
        verify(airportListPresenter.view, never())?.onLoadPageError(any())
        verify(airportListPresenter.view, never())?.onLoadPageSuccess(any(), any())
    }

}