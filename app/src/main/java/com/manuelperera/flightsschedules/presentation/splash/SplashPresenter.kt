package com.manuelperera.flightsschedules.presentation.splash

import com.manuelperera.flightsschedules.BuildConfig.CLIENT_ID
import com.manuelperera.flightsschedules.BuildConfig.CLIENT_SECRET
import com.manuelperera.flightsschedules.domain.extensions.check
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase.Params
import com.manuelperera.flightsschedules.presentation.base.Presenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val loginUseCase: LoginUseCase
) : Presenter<SplashView>() {

    fun login(
            clientId: String = CLIENT_ID,
            clientSecret: String = CLIENT_SECRET
    ) {
        addSubscription(loginUseCase(Params(clientId, clientSecret)).subscribe { either ->
            either.check(
                    { view?.onLoginError() },
                    { view?.onLoginSuccess() }
            )
        })
    }

}