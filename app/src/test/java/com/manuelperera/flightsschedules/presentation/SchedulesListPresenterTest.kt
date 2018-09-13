package com.manuelperera.flightsschedules.presentation

import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.model.getAirport
import com.manuelperera.flightsschedules.domain.model.getScheduleList
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRule
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherNoMoreData
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListPresenter
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListView
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
class SchedulesListPresenterTest {

    private lateinit var scheduleListPresenter: ScheduleListPresenter

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRule()
    @Mock
    private lateinit var getSchedulesUseCase: GetSchedulesUseCase
    @Mock
    private lateinit var scheduleListView: ScheduleListView

    @Before
    fun setUp() {
        scheduleListPresenter = ScheduleListPresenter(getSchedulesUseCase)
        scheduleListPresenter.init(scheduleListView)
    }

    @Test
    fun `getSchedulesPage when departureAirport and arrivalAirport are selected and different, should invoke onLoadPageSuccess`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")
        val isRefreshingList = false
        val scheduleList = getScheduleList()
        val page = 1
        whenever(getSchedulesUseCase(any())).doAnswer { getObEitherSuccess(scheduleList) }

        scheduleListPresenter.getSchedulesPage(page, isRefreshingList)

        assert(scheduleListPresenter.compositeDisposable.size() == 1)
        verify(scheduleListPresenter.view)?.onLoadPageSuccess(scheduleList, isRefreshingList)
        verify(scheduleListPresenter.view)?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(page == 1)
    }

    @Test
    fun `getSchedulesPage when departureAirport and arrivalAirport are selected but the same, should invoke showErrorConfigurationImage`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("MAD")
        val isRefreshingList = false

        scheduleListPresenter.getSchedulesPage(1, isRefreshingList)

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_different_airports)
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun `getSchedulesPage when departureAirport or arrivalAirport are not selected, should invoke showErrorConfigurationImage`() {
        val isRefreshingList = false

        scheduleListPresenter.getSchedulesPage(1, isRefreshingList)

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_airports)
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun `getSchedulesPage fail with Failure-Error when departureAirport and arrivalAirport are selected and different, should invoke onLoadPageError`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")
        val page = 1
        val isRefreshingList = false
        whenever(getSchedulesUseCase.invoke(any())).doAnswer { getObEitherError() }

        scheduleListPresenter.getSchedulesPage(page, isRefreshingList)

        assert(scheduleListPresenter.compositeDisposable.size() == 1)
        verify(scheduleListPresenter.view)?.onLoadPageError(any())
        verify(scheduleListPresenter.view)?.showSnackbar("Error")
        verify(scheduleListPresenter.view)?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(any())
    }

    @Test
    fun `getSchedulesPage fail with Failure-NoMoreData when departureAirport and arrivalAirport are selected and different, should invoke onFinishList`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")
        val page = 1
        val isRefreshingList = false
        whenever(getSchedulesUseCase.invoke(any())).doAnswer { getObEitherNoMoreData() }

        scheduleListPresenter.getSchedulesPage(page, isRefreshingList)

        assert(scheduleListPresenter.compositeDisposable.size() == 1)
        verify(scheduleListPresenter.view)?.onFinishList()
        verify(scheduleListPresenter.view)?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.showSnackbar("Error")
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(any())
    }

    @Test
    fun `checkAirportsAndGoToMap when departureAirport and arrivalAirport are selected and different, should invoke goToMap`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")

        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.goToMap(any(), any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
    }

    @Test
    fun `checkAirportsAndGoToMap when departureAirport and arrivalAirport are selected but the same, should invoke goToMap`() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("MAD")

        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_different_airports)
        verify(scheduleListPresenter.view, never())?.goToMap(any(), any())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun `checkAirportsAndGoToMap when departureAirport or arrivalAirport are not selected, should invoke goToMap`() {
        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_airports)
        verify(scheduleListPresenter.view, never())?.goToMap(any(), any())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(any())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(any(), any())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

}