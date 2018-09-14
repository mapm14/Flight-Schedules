package com.manuelperera.flightsschedules.presentation

import androidx.test.runner.AndroidJUnit4
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRuleAndroidTest
import com.manuelperera.flightsschedules.infrastructure.di.component.DaggerAppComponent
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class AirportsListPresenterIntegrationTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleAndroidTest()

    private val airportListPresenter = DaggerAppComponent.builder().build().provideAirportsListPresenter()

    @Before
    fun setUp() {
        @Suppress("UNCHECKED_CAST")
        airportListPresenter.init(mock(PagingView::class.java) as PagingView<Airport>)
    }

    @Test
    fun getAirportsPage_whenPageIsCorrect_shouldInvokeOnLoadPageSuccess() {
        val page = 1

        airportListPresenter.getAirportsPage(page)

        assert(airportListPresenter.compositeDisposable.size() == 1)
        verify(airportListPresenter.view)?.onLoadPageSuccess(anyList(), anyBoolean())
        verify(airportListPresenter.view, never())?.onLoadPageError(page == 1)
    }

}