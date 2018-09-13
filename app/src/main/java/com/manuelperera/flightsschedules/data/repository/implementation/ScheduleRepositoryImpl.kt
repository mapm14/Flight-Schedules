package com.manuelperera.flightsschedules.data.repository.implementation

import arrow.core.Either
import com.manuelperera.flightsschedules.data.repository.base.BaseRepository
import com.manuelperera.flightsschedules.data.repository.client.ScheduleApiClient
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.domain.repository.ScheduleRepository
import io.reactivex.Observable
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
        private val scheduleApiClient: ScheduleApiClient
) : BaseRepository(), ScheduleRepository {

    override fun getSchedules(origin: String,
                              destination: String,
                              fromDateTime: String,
                              limit: Int,
                              offset: Int): Observable<Either<Failure, List<Schedule>>> {

        return modifyObservable(scheduleApiClient.api.getSchedules(
                origin,
                destination,
                fromDateTime,
                limit,
                offset))
    }

}