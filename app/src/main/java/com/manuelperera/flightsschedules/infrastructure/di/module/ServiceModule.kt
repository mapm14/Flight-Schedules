package com.manuelperera.flightsschedules.infrastructure.di.module

import com.manuelperera.flightsschedules.data.service.ContextDataServiceImpl
import com.manuelperera.flightsschedules.domain.service.ContextDataService
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {

    @Binds
    abstract fun contextDataService(contextDataServiceImpl: ContextDataServiceImpl): ContextDataService

}