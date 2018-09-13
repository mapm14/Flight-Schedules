package com.manuelperera.flightsschedules.presentation.schedulelist

import androidx.annotation.StringRes
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingView

interface ScheduleListView : PagingView<Schedule> {

    fun goToMap(departureAirport: Airport, arrivalAirport: Airport)

    fun showErrorConfigurationImage(@StringRes title: Int)

    fun hideErrorConfigurationImage()

}