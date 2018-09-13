package com.manuelperera.flightsschedules.domain.model.schedule

import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.ITEM
import java.util.Date

class Schedule(
        val flightList: List<Flight>,
        val totalDuration: String
) : PagingObject {

    override var itemViewType = ITEM

    class Flight(
            val departure: City,
            val arrival: City,
            val details: Details
    )

    class City(
            val airportCode: String,
            val scheduledTimeLocal: Date
    )

    class Details(
            val flightNumber: String,
            val stopQuantity: Int,
            val daysOfOperation: Int
    )

}