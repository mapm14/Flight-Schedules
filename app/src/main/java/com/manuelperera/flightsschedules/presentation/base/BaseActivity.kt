package com.manuelperera.flightsschedules.presentation.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.annotation.LayoutRes
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.snackbar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<P : Presenter<V>, V : View> : DaggerAppCompatActivity(), View {

    protected open lateinit var presenter: P
    protected open lateinit var view: V
    @set:LayoutRes
    abstract var activityLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        presenter.init(view)
    }

    override fun onDestroy() {
        presenter.clear()
        super.onDestroy()
    }

    override fun showToast(message: String, resId: Int) {
        if (resId == -1) makeText(this, message, LENGTH_LONG).show()
        else makeText(this, getString(resId), LENGTH_LONG).show()
    }

    override fun showSnackbarWithRes(title: Int, action: Int, length: Int, actionResult: () -> Unit) {
        val container = findViewById<android.view.View?>(R.id.parentContainer)
        container?.snackbar(title, action, length, actionResult = actionResult)
    }

    override fun showSnackbar(title: String, action: String, length: Int, actionResult: () -> Unit) {
        val container = findViewById<android.view.View?>(R.id.parentContainer)
        container?.snackbar(title, action, length, actionResult = actionResult)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}