package com.manuelperera.flightsschedules.domain.model

import com.manuelperera.flightsschedules.data.entity.error.ErrorResponse.Companion.DEFAULT_CODE_ERROR
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.CallInfo
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule.City
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule.Details
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule.Flight
import java.util.Date

fun getScheduleList() =
        listOf(getSchedule())

fun getSchedule() =
        Schedule(listOf(Flight(City("", Date()), City("", Date()), Details("", 0, 0))),
                "")

fun getAirportList() =
        listOf(getAirport())

fun getAirport(airportCode: String = "") =
        Airport(airportCode, 0.0, 0.0, "", "", "", "", 0.0, "")

fun getCallInfo(code: Int = DEFAULT_CODE_ERROR, message: String = "Error") =
        CallInfo(code, message)

fun getFailureError() =
        Failure.Error(getCallInfo())

fun getNoMoreData() =
        Failure.NoMoreData(getCallInfo())