package com.manuelperera.flightsschedules.infrastructure

import com.manuelperera.flightsschedules.infrastructure.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class FlightsApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.create()

}