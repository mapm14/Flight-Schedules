package com.manuelperera.flightsschedules.data.entity.schedule

import com.google.gson.annotations.SerializedName
import com.manuelperera.flightsschedules.domain.model.base.ResponseObject
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import java.util.Date

class ScheduleResponse(
        @SerializedName("ScheduleResource") val scheduleResource: ScheduleResource = ScheduleResource()
) : ResponseObject<List<Schedule>> {

    override fun toAppDomain(): List<Schedule> {
        val scheduleList = mutableListOf<Schedule>()
        scheduleResource.scheduleList.forEach { schedule ->
            val flightList = mutableListOf<Schedule.Flight>()
            schedule.flightList.forEach { flight ->
                with(flight) {
                    flightList.add(Schedule.Flight(
                            Schedule.City(departure.airportCode, departure.scheduledTimeLocal.dateTime),
                            Schedule.City(arrival.airportCode, arrival.scheduledTimeLocal.dateTime),
                            Schedule.Details(marketingCarrier.flightNumber, details.stops.stopQuantity, details.daysOfOperation)
                    ))
                }
            }
            scheduleList.add(Schedule(flightList, schedule.totalJourney.duration))
        }
        return scheduleList
    }

    class ScheduleResource(
            @SerializedName("Schedule") val scheduleList: List<Schedule> = listOf(),
            @SerializedName("Meta") val meta: Meta = Meta()
    ) {

        class Schedule(
                @SerializedName("TotalJourney") val totalJourney: TotalJourney = TotalJourney(),
                @SerializedName("Flight") val flightList: List<Flight> = listOf()
        ) {

            class TotalJourney(
                    @SerializedName("Duration") val duration: String = ""
            )

            class Flight(
                    @SerializedName("Departure") val departure: Departure = Departure(),
                    @SerializedName("Arrival") val arrival: Arrival = Arrival(),
                    @SerializedName("MarketingCarrier") val marketingCarrier: MarketingCarrier = MarketingCarrier(),
                    @SerializedName("Equipment") val equipment: Equipment = Equipment(),
                    @SerializedName("Details") val details: Details = Details()
            ) {

                class Details(
                        @SerializedName("Stops") val stops: Stops = Stops(),
                        @SerializedName("DaysOfOperation") val daysOfOperation: Int = 0,
                        @SerializedName("DatePeriod") val datePeriod: DatePeriod = DatePeriod()
                ) {

                    class Stops(
                            @SerializedName("StopQuantity") val stopQuantity: Int = 0
                    )


                    class DatePeriod(
                            @SerializedName("Effective") val effective: String = "",
                            @SerializedName("Expiration") val expiration: String = ""
                    )
                }


                class MarketingCarrier(
                        @SerializedName("AirlineID") val airlineID: String = "",
                        @SerializedName("FlightNumber") val flightNumber: String = ""
                )


                class Equipment(
                        @SerializedName("AircraftCode") val aircraftCode: String = ""
                )


                class Arrival(
                        @SerializedName("AirportCode") val airportCode: String = "",
                        @SerializedName("ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal = ScheduledTimeLocal(),
                        @SerializedName("Terminal") val terminal: Terminal = Terminal()
                ) {

                    class ScheduledTimeLocal(
                            @SerializedName("DateTime") val dateTime: Date = Date()
                    )


                    class Terminal(
                            @SerializedName("Name") val name: String = ""
                    )
                }


                class Departure(
                        @SerializedName("AirportCode") val airportCode: String = "",
                        @SerializedName("ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal = ScheduledTimeLocal()
                ) {

                    class ScheduledTimeLocal(
                            @SerializedName("DateTime") val dateTime: Date = Date()
                    )
                }
            }
        }


        class Meta(
                @SerializedName("@Version") val version: String = "",
                @SerializedName("Link") val link: List<Link> = listOf()
        ) {

            class Link(
                    @SerializedName("@Href") val href: String = "",
                    @SerializedName("@Rel") val rel: String = ""
            )
        }
    }
}