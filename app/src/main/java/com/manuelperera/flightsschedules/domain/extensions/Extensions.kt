package com.manuelperera.flightsschedules.domain.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import arrow.core.Either
import com.google.android.material.snackbar.Snackbar
import com.manuelperera.flightsschedules.domain.model.base.Failure
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.gone() {
    visibility = GONE
}

fun View.visible() {
    visibility = VISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.snackbar(title: String = "",
                  action: String = "",
                  length: Int = Snackbar.LENGTH_LONG,
                  @ColorRes actionColor: Int = android.R.color.white,
                  actionResult: () -> Unit = {}) {

    val snackbar = Snackbar.make(this, title, length)

    if (action.isNotEmpty()) {
        snackbar.setAction(action) { actionResult() }
        snackbar.setActionTextColor(ContextCompat.getColor(context, actionColor))
    }
    snackbar.show()
}

fun View.snackbar(@StringRes titleRes: Int = 0,
                  @StringRes actionRes: Int = 0,
                  length: Int = Snackbar.LENGTH_LONG,
                  @ColorRes actionColor: Int = android.R.color.white,
                  actionResult: () -> Unit = {}) {

    snackbar(context.getString(titleRes), context.getString(actionRes), length, actionColor, actionResult)

}

fun Date.format(pattern: String = "yyyy-MM-dd"): String =
        SimpleDateFormat(pattern, Locale.US).format(this)

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? =
        if (p1 != null && p2 != null) block(p1, p2) else null

fun <T : Any> Either<Failure, T>.check(right: (T) -> Unit, left: (Failure) -> Unit) {
    when (this) {
        is Either.Left -> left(a)
        is Either.Right -> right(b)
    }
}