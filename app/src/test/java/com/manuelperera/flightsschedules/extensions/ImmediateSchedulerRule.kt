package com.manuelperera.flightsschedules.extensions

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ImmediateSchedulerRule : TestRule {

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) =
                super.scheduleDirect(run, 0, unit)

        override fun createWorker() =
                ExecutorScheduler.ExecutorWorker(Executor { it.run() })
    }

    override fun apply(base: Statement, d: Description) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

            try {
                base.evaluate()
            } finally {
                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }

}