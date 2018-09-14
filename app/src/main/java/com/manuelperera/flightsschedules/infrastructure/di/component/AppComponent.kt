package com.manuelperera.flightsschedules.infrastructure.di.component

import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.infrastructure.FlightsApp
import com.manuelperera.flightsschedules.infrastructure.di.module.AppModule
import com.manuelperera.flightsschedules.presentation.splash.SplashPresenter
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<FlightsApp> {

    fun provideLoginRepository(): LoginRepository

    fun provideSplashPresenter(): SplashPresenter

}