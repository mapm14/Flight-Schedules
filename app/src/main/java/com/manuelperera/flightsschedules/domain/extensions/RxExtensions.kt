package com.manuelperera.flightsschedules.domain.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T : Any> Observable<T>.subObs(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <T : Any> Observable<T>.subNewObs(): Observable<T> =
        subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())