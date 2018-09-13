package com.manuelperera.flightsschedules.domain.repository

import arrow.core.Either
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import io.reactivex.Observable

interface ScheduleRepository {

    fun getSchedules(
            origin: String,
            destination: String,
            fromDateTime: String,
            limit: Int,
            offset: Int
    ): Observable<Either<Failure, List<Schedule>>>

}