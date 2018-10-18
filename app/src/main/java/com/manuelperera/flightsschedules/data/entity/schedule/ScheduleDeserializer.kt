package com.manuelperera.flightsschedules.data.entity.schedule

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ScheduleDeserializer : JsonDeserializer<ScheduleResponse.ScheduleResource.Schedule> {

    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm")
            .create()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ScheduleResponse.ScheduleResource.Schedule {
        context?.let {
            val flightElement = json?.asJsonObject?.get("Flight")
            val totalJourneyElement = json?.asJsonObject?.get("TotalJourney")
            val totalJourney = gson.fromJson(totalJourneyElement, ScheduleResponse.ScheduleResource.Schedule.TotalJourney::class.java)

            return when (flightElement) {
                is JsonArray -> {
                    ScheduleResponse.ScheduleResource.Schedule(
                            totalJourney,
                            gson.fromJson(flightElement, Array<ScheduleResponse.ScheduleResource.Schedule.Flight>::class.java).toList()
                    )
                }
                is JsonObject -> {
                    ScheduleResponse.ScheduleResource.Schedule(
                            totalJourney,
                            listOf(gson.fromJson(flightElement, ScheduleResponse.ScheduleResource.Schedule.Flight::class.java))
                    )
                }
                else -> throw JsonParseException("ScheduleDeserializer: Unsupported type of Schedule element")
            }
        } ?: throw JsonParseException("ScheduleDeserializer: JsonDeserializationContext is null")
    }

}