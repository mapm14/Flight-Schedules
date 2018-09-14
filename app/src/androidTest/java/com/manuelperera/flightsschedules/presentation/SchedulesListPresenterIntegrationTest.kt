package com.manuelperera.flightsschedules.presentation

import androidx.test.runner.AndroidJUnit4
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRuleAndroidTest
import com.manuelperera.flightsschedules.infrastructure.di.component.DaggerAppComponent
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class SchedulesListPresenterIntegrationTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleAndroidTest()

    private val scheduleListPresenter = DaggerAppComponent.builder().build().provideSchedulesListPresenter()

    @Before
    fun setUp() {
        scheduleListPresenter.init(mock(ScheduleListView::class.java))
    }

    private fun getAirport(airportCode: String = ""): Airport =
            Airport(airportCode, 0.0, 0.0, "", "", "", "", 0.0, "")

    @Test
    fun getSchedulesPage_whenHasDepartureAndArrivalAirport_shouldInvokeOnLoadPageSuccess() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")

        scheduleListPresenter.getSchedulesPage(1)

        assert(scheduleListPresenter.compositeDisposable.size() == 1)
        verify(scheduleListPresenter.view)?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view)?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(anyInt())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
    }

    @Test
    fun getSchedulesPage_whenDepartureAndArrivalAirportAreTheSame_shouldInvokeShowErrorConfigurationImage() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("MAD")

        scheduleListPresenter.getSchedulesPage(1)

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_different_airports)
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun getSchedulesPage_whenNoDepartureOrArrivalAirport_shouldInvokeShowErrorConfigurationImage() {
        scheduleListPresenter.getSchedulesPage(1)

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_airports)
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun checkAirportsAndGoToMap_whenHasDepartureAndArrivalAirport_shouldInvokeGoToMap() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("BCN")

        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 1)
        verify(scheduleListPresenter.view)?.goToMap(scheduleListPresenter.departureAirport!!, scheduleListPresenter.arrivalAirport!!)
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
        verify(scheduleListPresenter.view, never())?.showErrorConfigurationImage(anyInt())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
    }

    @Test
    fun checkAirportsAndGoToMap_whenDepartureAndArrivalAirportAreTheSame_shouldInvokeShowErrorConfigurationImage() {
        scheduleListPresenter.departureAirport = getAirport("MAD")
        scheduleListPresenter.arrivalAirport = getAirport("MAD")

        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_different_airports)
        verify(scheduleListPresenter.view, never())?.goToMap(getAirport(), getAirport())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

    @Test
    fun checkAirportsAndGoToMap_whenNoDepartureOrArrivalAirport_shouldInvokeShowErrorConfigurationImage() {
        scheduleListPresenter.checkAirportsAndGoToMap()

        assert(scheduleListPresenter.compositeDisposable.size() == 0)
        verify(scheduleListPresenter.view)?.showErrorConfigurationImage(R.string.choose_airports)
        verify(scheduleListPresenter.view, never())?.goToMap(getAirport(), getAirport())
        verify(scheduleListPresenter.view, never())?.onLoadPageError(anyBoolean())
        verify(scheduleListPresenter.view, never())?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(scheduleListPresenter.view, never())?.hideErrorConfigurationImage()
    }

}