package com.manuelperera.flightsschedules.infrastructure.di.module

import com.manuelperera.flightsschedules.data.repository.implementation.AirportRepositoryImpl
import com.manuelperera.flightsschedules.data.repository.implementation.LoginRepositoryImpl
import com.manuelperera.flightsschedules.data.repository.implementation.ScheduleRepositoryImpl
import com.manuelperera.flightsschedules.domain.repository.AirportRepository
import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun loginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun scheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    abstract fun airportRepository(airportRepositoryImpl: AirportRepositoryImpl): AirportRepository

}