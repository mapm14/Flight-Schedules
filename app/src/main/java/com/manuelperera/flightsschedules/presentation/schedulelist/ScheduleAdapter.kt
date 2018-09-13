package com.manuelperera.flightsschedules.presentation.schedulelist

import android.view.LayoutInflater
import android.view.View
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.format
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingAdapter
import kotlinx.android.synthetic.main.item_flight.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleAdapter(
        onItemClick: (Schedule) -> Unit,
        onRetryClick: () -> Unit
) : PagingAdapter<Schedule>(onRetryClick) {

    override var itemLayout = R.layout.item_schedule

    override var onBindItem: (View, Schedule) -> Unit = { itemView, schedule ->
        with(itemView) {
            scheduleContainer.removeAllViews()
            val inflater = LayoutInflater.from(context)

            totalDuration.text = context.getString(R.string.duration, schedule.totalDuration)

            schedule.flightList.forEach { flight ->
                val flightItem = inflater.inflate(R.layout.item_flight, scheduleContainer, false)

                with(flightItem) {
                    departureAirportTxt.text = flight.departure.airportCode
                    departureDateTxt.text = flight.departure.scheduledTimeLocal.format("dd/MM/yy")
                    departureHourTxt.text = flight.departure.scheduledTimeLocal.format("HH:mm")

                    arrivalAirportTxt.text = flight.arrival.airportCode
                    arrivalDateTxt.text = flight.arrival.scheduledTimeLocal.format("dd/MM/yy")
                    arrivalHourTxt.text = flight.arrival.scheduledTimeLocal.format("HH:mm")
                }

                scheduleContainer.addView(flightItem)
            }

            setOnClickListener { onItemClick(schedule) }
        }
    }

}