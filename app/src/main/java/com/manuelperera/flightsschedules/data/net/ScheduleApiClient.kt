package com.manuelperera.flightsschedules.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.manuelperera.flightsschedules.BuildConfig.BASE_URL
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResourceDeserializer
import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse
import com.manuelperera.flightsschedules.data.repository.datasources.api.schedules.ScheduleApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ScheduleApiClient @Inject constructor(baseHttpClient: BaseHttpClient) {

    private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(ScheduleResponse.ScheduleResource::class.java, ScheduleResourceDeserializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm")
            .create()

    val api: ScheduleApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(baseHttpClient.getClient())
            .build()
            .create(ScheduleApi::class.java)

}