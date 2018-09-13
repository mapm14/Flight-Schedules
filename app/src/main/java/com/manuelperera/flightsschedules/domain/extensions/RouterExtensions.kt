package com.manuelperera.flightsschedules.domain.extensions

import android.content.Intent
import android.os.Parcelable
import com.manuelperera.flightsschedules.presentation.base.BaseActivity
import com.manuelperera.flightsschedules.presentation.base.Presenter
import com.manuelperera.flightsschedules.presentation.base.View
import java.io.Serializable

//region BaseActivity extensions

inline fun <reified T : Parcelable> Intent.setParcelableParam(obj: T, tag: String = "") {
    putExtra(T::class.java.name + tag, obj)
}

inline fun <reified T : Parcelable, P : Presenter<V>, V : View> BaseActivity<P, V>.getParcelableParam(data: Intent = intent, tag: String = ""): T =
        data.extras?.getParcelable(T::class.java.name + tag) as T

inline fun <reified T : Parcelable> Intent.getParcelableParam(tag: String = ""): T =
        getParcelableExtra(T::class.java.name + tag) as T

inline fun <reified T : Serializable> Intent.setSerializableParam(obj: T, tag: String = "") {
    putExtra(T::class.java.name + tag, obj)
}

inline fun <reified T : Serializable, P : Presenter<V>, V : View> BaseActivity<P, V>.getSerializableParam(data: Intent = intent, tag: String = ""): T =
        data.extras?.getSerializable(T::class.java.name + tag) as T

inline fun <reified T : Serializable> Intent.getSerializableParam(tag: String = ""): T =
        getSerializableExtra(T::class.java.name + tag) as T

//endregion

//region BaseFragment extensions

//inline fun <reified T : Parcelable> BaseFragment.setParcelableParam(obj: T, tag: String = "") {
//    if (arguments == null) {
//        arguments = android.os.Bundle()
//    }
//    arguments?.putSerializable(T::class.java.name + tag, obj)
//}
//
//inline fun <reified T : Parcelable> BaseFragment.getParcelableParam(tag: String = ""): T =
//        arguments?.getSerializable(T::class.java.name + tag) as T

//endregion
