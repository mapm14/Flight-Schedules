package com.manuelperera.flightsschedules.data.repository.datasources.api.airports

import com.manuelperera.flightsschedules.data.repository.datasources.api.airports.model.AirportResponse
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportApi {

    @GET("references/airports")
    fun getAirprots(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int,
            @Query("LHoperated") lhOperated: String = "0",
            @Query("lang") lang: String = "en"
    ): Observable<Result<AirportResponse>>

}