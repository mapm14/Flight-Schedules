package com.manuelperera.flightsschedules.infrastructure.di.module

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    ServiceModule::class])
class AppModule