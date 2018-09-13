package com.manuelperera.flightsschedules.presentation.base

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG

interface View {

    fun showToast(message: String, resId: Int = -1)

    fun showSnackbarWithRes(@StringRes title: Int, @StringRes action: Int = android.R.string.ok, length: Int = LENGTH_LONG, actionResult: () -> Unit = {})

    fun showSnackbar(title: String, action: String = "Ok", length: Int = LENGTH_LONG, actionResult: () -> Unit = {})

}