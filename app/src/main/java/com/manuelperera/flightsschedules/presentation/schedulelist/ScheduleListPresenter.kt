package com.manuelperera.flightsschedules.presentation.schedulelist

import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.extensions.safeLet
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.base.Failure.NoMoreData
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase.Params
import com.manuelperera.flightsschedules.presentation.base.Presenter
import javax.inject.Inject

class ScheduleListPresenter @Inject constructor(
        private val getSchedulesUseCase: GetSchedulesUseCase
) : Presenter<ScheduleListView>() {

    private var defaultOffset = 10

    var departureAirport: Airport? = null
    var arrivalAirport: Airport? = null

    fun getSchedulesPage(page: Int, isRefreshingList: Boolean = false) {
        checkAirportsAndExecute { departure, arrival ->
            view?.hideErrorConfigurationImage()

            val offset = page * defaultOffset
            val isFirstPage = offset == 0

            addSubscription(getSchedulesUseCase(Params(departure.airportCode, arrival.airportCode, defaultOffset, offset)).subscribe { either ->
                either.check(
                        { onGetPageFailure(it, isFirstPage) },
                        { view?.onLoadPageSuccess(it, isRefreshingList) }
                )
            })
        }
    }

    fun checkAirportsAndGoToMap() {
        checkAirportsAndExecute { departure, arrival ->
            view?.goToMap(departure, arrival)
        }
    }

    private fun onGetPageFailure(failure: Failure, isFirstPage: Boolean) {
        when (failure) {
            is NoMoreData -> view?.onFinishList()
            else -> {
                view?.onLoadPageError(isFirstPage)
                view?.showSnackbar(failure.callInfo.message)
            }
        }
    }

    private fun checkAirportsAndExecute(execute: (Airport, Airport) -> Unit) {
        safeLet(departureAirport, arrivalAirport) { departure, arrival ->
            if (departure.airportCode != arrival.airportCode) {
                execute(departure, arrival)
            } else {
                view?.showErrorConfigurationImage(R.string.choose_different_airports)
            }
        } ?: view?.showErrorConfigurationImage(R.string.choose_airports)
    }

}