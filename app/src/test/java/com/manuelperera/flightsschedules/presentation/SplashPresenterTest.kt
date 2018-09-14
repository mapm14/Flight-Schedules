package com.manuelperera.flightsschedules.presentation

import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRuleUnitTests
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.manuelperera.flightsschedules.presentation.splash.SplashPresenter
import com.manuelperera.flightsschedules.presentation.splash.SplashView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {

    private lateinit var splashPresenter: SplashPresenter

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()
    @Mock
    private lateinit var loginUseCase: LoginUseCase
    @Mock
    private lateinit var splashView: SplashView

    @Before
    fun setUp() {
        splashPresenter = SplashPresenter(loginUseCase)
        splashPresenter.init(splashView)
    }

    @Test
    fun `login success should invoke onLoginSuccess`() {
        val accessToken = "d3ukap6h6ym9pchzf3ax6nga"
        whenever(loginUseCase(any())).doAnswer { getObEitherSuccess(accessToken) }

        splashPresenter.login()

        assert(splashPresenter.compositeDisposable.size() == 1)
        verify(splashPresenter.view)?.onLoginSuccess()
        verify(splashPresenter.view, never())?.onLoginError()
    }

    @Test
    fun `login fail should invoke onLoginError`() {
        whenever(loginUseCase(any())).doAnswer { getObEitherError() }

        splashPresenter.login()

        assert(splashPresenter.compositeDisposable.size() == 1)
        verify(splashPresenter.view)?.onLoginError()
        verify(splashPresenter.view, never())?.onLoginSuccess()
    }

}