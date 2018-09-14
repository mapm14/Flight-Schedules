package com.manuelperera.flightsschedules.infrastructure.di.component

import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.infrastructure.FlightsApp
import com.manuelperera.flightsschedules.infrastructure.di.module.ActivityModule
import com.manuelperera.flightsschedules.infrastructure.di.module.RepositoryModule
import com.manuelperera.flightsschedules.infrastructure.di.module.ServiceModule
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListPresenter
import com.manuelperera.flightsschedules.presentation.selectairport.AirportListPresenter
import com.manuelperera.flightsschedules.presentation.splash.SplashPresenter
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    ServiceModule::class
])
interface AppComponent : AndroidInjector<FlightsApp> {

    fun provideLoginRepository(): LoginRepository

    fun provideSplashPresenter(): SplashPresenter

    fun provideSchedulesListPresenter(): ScheduleListPresenter

    fun provideAirportsListPresenter(): AirportListPresenter

}