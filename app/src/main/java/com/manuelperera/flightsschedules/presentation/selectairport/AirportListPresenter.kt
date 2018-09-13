package com.manuelperera.flightsschedules.presentation.selectairport

import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.base.Failure.NoMoreData
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase
import com.manuelperera.flightsschedules.domain.usecase.airport.GetAirportsUseCase.Params
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingView
import com.manuelperera.flightsschedules.presentation.base.Presenter
import javax.inject.Inject

class AirportListPresenter @Inject constructor(
        private val getAirportsUseCase: GetAirportsUseCase
) : Presenter<PagingView<Airport>>() {

    private var defaultOffset = 20
    var selectedAirport: Airport? = null

    fun getAirportsPage(page: Int) {
        val offset = page * defaultOffset
        val isFirstPage = offset == 0

        addSubscription(
                getAirportsUseCase(Params(defaultOffset, offset)).subscribe { either ->
                    either.check(
                            { view?.onLoadPageSuccess(it, false) },
                            { onGetPageFailure(it, isFirstPage) }
                    )
                }
        )
    }

    private fun onGetPageFailure(failure: Failure, isFirstPage: Boolean) {
        when (failure) {
            is NoMoreData -> view?.onFinishList()
            else -> {
                view?.onLoadPageError(isFirstPage)
                view?.showSnackbar(title = failure.callInfo.message)
            }
        }
    }

}