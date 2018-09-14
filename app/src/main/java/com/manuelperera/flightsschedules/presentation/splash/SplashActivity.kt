package com.manuelperera.flightsschedules.presentation.splash

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.gone
import com.manuelperera.flightsschedules.domain.extensions.visible
import com.manuelperera.flightsschedules.presentation.base.BaseActivity
import com.manuelperera.flightsschedules.presentation.schedulelist.ScheduleListActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashPresenter, SplashView>(), SplashView {

    @Inject
    override lateinit var presenter: SplashPresenter

    override var view: SplashView = this
    override var activityLayout: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.login()
    }

    override fun onLoginSuccess() {
        val intent = ScheduleListActivity.getIntent(this)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onLoginError() {
        spinKit.gone()
        showSnackbarWithRes(R.string.login_failed, R.string.retry, LENGTH_INDEFINITE) {
            spinKit.visible()
            presenter.login()
        }
    }

}