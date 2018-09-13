package com.manuelperera.flightsschedules.presentation.splash

import arrow.core.None
import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase
import com.manuelperera.flightsschedules.presentation.base.Presenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val loginUseCase: LoginUseCase
) : Presenter<SplashView>() {

    override fun init() {
        login()
    }

    fun login() {
        addSubscription(loginUseCase(None).subscribe { either ->
            either.check(
                    { view?.onLoginSuccess() },
                    { view?.onLoginError() }
            )
        })
    }

}