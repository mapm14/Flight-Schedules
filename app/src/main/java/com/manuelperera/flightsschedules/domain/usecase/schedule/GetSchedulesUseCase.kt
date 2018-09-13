package com.manuelperera.flightsschedules.domain.usecase.schedule

import com.manuelperera.flightsschedules.domain.extensions.format
import com.manuelperera.flightsschedules.domain.extensions.subObs
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.domain.repository.ScheduleRepository
import com.manuelperera.flightsschedules.domain.usecase.base.UseCase
import com.manuelperera.flightsschedules.domain.usecase.schedule.GetSchedulesUseCase.Params
import java.util.Calendar
import javax.inject.Inject

class GetSchedulesUseCase @Inject constructor(
        private val scheduleRepository: ScheduleRepository
) : UseCase<List<Schedule>, Params> {

    override fun invoke(params: Params) =
            scheduleRepository.getSchedules(
                    params.origin,
                    params.destination,
                    params.fromDateTime,
                    params.limit,
                    params.offset
            ).subObs()

    class Params(val origin: String,
                 val destination: String,
                 val limit: Int = 10,
                 val offset: Int,
                 val fromDateTime: String = Calendar.getInstance().time.format())

}