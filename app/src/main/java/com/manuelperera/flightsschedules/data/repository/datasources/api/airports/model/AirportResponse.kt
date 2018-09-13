package com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model

import com.google.gson.annotations.SerializedName
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.base.ResponseObject

class AirportResponse(
        @SerializedName("AirportResource") val airportResource: AirportResource = AirportResource()
) : ResponseObject<List<Airport>> {

    override fun toAppDomain(): List<Airport> {
        val airportList = mutableListOf<Airport>()
        with(airportResource.airports.airportList) {
            forEach {
                airportList.add(
                        Airport(
                                it.airportCode,
                                it.position.coordinate.latitude,
                                it.position.coordinate.longitude,
                                it.cityCode,
                                it.countryCode,
                                it.locationType,
                                it.names.name.name,
                                it.utcOffset,
                                it.timeZoneId
                        )
                )
            }
        }
        return airportList
    }

    class AirportResource(
            @SerializedName("Airports") val airports: Airports = Airports(),
            @SerializedName("Meta") val meta: Meta = Meta()
    ) {

        class Meta(
                @SerializedName("@Version") val version: String = "",
                @SerializedName("Link") val link: List<Link> = listOf(),
                @SerializedName("TotalCount") val totalCount: Int = 0
        ) {

            class Link(
                    @SerializedName("@Href") val href: String = "",
                    @SerializedName("@Rel") val rel: String = ""
            )

        }

        class Airports(
                @SerializedName("Airport") val airportList: List<Airport> = listOf()
        ) {

            class Airport(
                    @SerializedName("AirportCode") val airportCode: String = "",
                    @SerializedName("Position") val position: Position = Position(),
                    @SerializedName("CityCode") val cityCode: String = "",
                    @SerializedName("CountryCode") val countryCode: String = "",
                    @SerializedName("LocationType") val locationType: String = "",
                    @SerializedName("Names") val names: Names = Names(),
                    @SerializedName("UtcOffset") val utcOffset: Double = 0.0,
                    @SerializedName("TimeZoneId") val timeZoneId: String = ""
            ) {

                class Names(
                        @SerializedName("Name") val name: Name = Name()
                ) {

                    class Name(
                            @SerializedName("@LanguageCode") val languageCode: String = "",
                            @SerializedName("$") val name: String = ""
                    )

                }

                class Position(
                        @SerializedName("Coordinate") val coordinate: Coordinate = Coordinate()
                ) {

                    class Coordinate(
                            @SerializedName("Latitude") val latitude: Double = 0.0,
                            @SerializedName("Longitude") val longitude: Double = 0.0
                    )

                }

            }

        }

    }

}