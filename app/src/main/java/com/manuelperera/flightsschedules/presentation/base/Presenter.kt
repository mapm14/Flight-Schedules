package com.manuelperera.flightsschedules.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Presenter<V : View> {

    lateinit var compositeDisposable: CompositeDisposable
        private set
    var view: V? = null
        private set

    fun init(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
        init()
    }

    fun clear() {
        view = null
        compositeDisposable.dispose()
    }

    protected fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected open fun init() {}

}