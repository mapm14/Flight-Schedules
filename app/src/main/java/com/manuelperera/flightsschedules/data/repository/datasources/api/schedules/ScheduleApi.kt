package com.manuelperera.flightsschedules.data.repository.datasources.api.schedules

import com.manuelperera.flightsschedules.data.entity.schedule.ScheduleResponse
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getSchedules(
            @Path("origin") origin: String,
            @Path("destination") destination: String,
            @Path("fromDateTime") fromDateTime: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int,
            @Query("directFlights") directFlights: String = "0"
    ): Observable<Result<ScheduleResponse>>

}