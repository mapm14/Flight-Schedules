package com.manuelperera.flightsschedules.infrastructure.di.module

import com.manuelperera.flightsschedules.infrastructure.di.scope.ViewScope
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListActivity
import com.manuelperera.flightsschedules.presentation.selectairport.AirportListActivity
import com.manuelperera.flightsschedules.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ViewScope
    @ContributesAndroidInjector
    abstract fun splashActivityInjector(): SplashActivity

    @ViewScope
    @ContributesAndroidInjector
    abstract fun schedulesListActivityInjector(): ScheduleListActivity

    @ViewScope
    @ContributesAndroidInjector
    abstract fun airportsListActivityInjector(): AirportListActivity

}