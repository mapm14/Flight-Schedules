package com.manuelperera.flightsschedules.presentation

import androidx.test.runner.AndroidJUnit4
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRuleAndroidTest
import com.manuelperera.flightsschedules.infrastructure.di.component.DaggerAppComponent
import com.manuelperera.flightsschedules.presentation.splash.SplashView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class SplashPresenterIntegrationTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleAndroidTest()

    private val splashPresenter = DaggerAppComponent.builder().build().provideSplashPresenter()

    @Before
    fun setUp() {
        splashPresenter.init(mock(SplashView::class.java))
    }

    @Test
    fun init_whenLoginSuccess_shouldInvokeOnLoginSuccess() {
        splashPresenter.login()

        assert(splashPresenter.compositeDisposable.size() == 1)
        verify(splashPresenter.view)?.onLoginSuccess()
        verify(splashPresenter.view, never())?.onLoginError()
    }

    @Test
    fun init_whenLoginFail_shouldInvokeOnLoginError() {
        splashPresenter.login("noClient", "noSecret")

        assert(splashPresenter.compositeDisposable.size() == 1)
        verify(splashPresenter.view)?.onLoginError()
        verify(splashPresenter.view, never())?.onLoginSuccess()
    }

}