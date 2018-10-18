package com.manuelperera.flightsschedules.data.repository.model

import com.manuelperera.flightsschedules.data.entity.airport.AirportResponse
import com.manuelperera.flightsschedules.data.entity.airport.AirportResponse.AirportResource
import com.manuelperera.flightsschedules.data.entity.airport.AirportResponse.AirportResource.Airports
import com.manuelperera.flightsschedules.data.entity.airport.AirportResponse.AirportResource.Airports.Airport
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse.ScheduleResource
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse.ScheduleResource.Schedule
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse.ScheduleResource.Schedule.Flight

fun getScheduleResponse(): ScheduleResponse =
        ScheduleResponse(ScheduleResource(listOf(Schedule(flightList = listOf(Flight())))))

fun getAirportResponse(): AirportResponse =
        AirportResponse(AirportResource(Airports(listOf(Airport()))))