package com.manuelperera.flightsschedules.data.repository.model

import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model.AirportResponse
import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model.AirportResponse.AirportResource
import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model.AirportResponse.AirportResource.Airports
import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model.AirportResponse.AirportResource.Airports.Airport
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.model.ScheduleResponse
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.model.ScheduleResponse.ScheduleResource
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.model.ScheduleResponse.ScheduleResource.Schedule
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.model.ScheduleResponse.ScheduleResource.Schedule.Flight

fun getScheduleResponse(): ScheduleResponse =
        ScheduleResponse(ScheduleResource(listOf(Schedule(flightList = listOf(Flight())))))

fun getAirportResponse(): AirportResponse =
        AirportResponse(AirportResource(Airports(listOf(Airport()))))