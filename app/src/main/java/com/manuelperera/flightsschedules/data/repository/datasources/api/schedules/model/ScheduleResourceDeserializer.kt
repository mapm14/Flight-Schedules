package com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.model

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ScheduleResourceDeserializer : JsonDeserializer<ScheduleResponse.ScheduleResource> {

    private val gson = GsonBuilder().
            setDateFormat("yyyy-MM-dd'T'HH:mm")
            .registerTypeAdapter(ScheduleResponse.ScheduleResource.Schedule::class.java, ScheduleDeserializer())
            .create()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ScheduleResponse.ScheduleResource {
        context?.let {
            val scheduleElement = json?.asJsonObject?.get("Schedule")
            val metaElement = json?.asJsonObject?.get("Meta")
            val meta = gson.fromJson(metaElement, ScheduleResponse.ScheduleResource.Meta::class.java)

            return when (scheduleElement) {
                is JsonArray -> {
                    ScheduleResponse.ScheduleResource(
                            gson.fromJson(scheduleElement, Array<ScheduleResponse.ScheduleResource.Schedule>::class.java).toList(),
                            meta
                    )
                }
                is JsonObject -> {
                    ScheduleResponse.ScheduleResource(
                            listOf(gson.fromJson(scheduleElement, ScheduleResponse.ScheduleResource.Schedule::class.java)),
                            meta
                    )
                }
                else -> throw JsonParseException("ScheduleResourceDeserializer: Unsupported type of ScheduleResource element")
            }
        }
                ?: throw JsonParseException("ScheduleResourceDeserializer: JsonDeserializationContext is null")

    }

}