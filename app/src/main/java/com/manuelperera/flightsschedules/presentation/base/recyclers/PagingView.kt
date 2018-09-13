package com.manuelperera.flightsschedules.presentation.base.recyclers

import com.manuelperera.flightsschedules.presentation.base.View

interface PagingView<T : PagingObject> : View {

    fun onLoadPageSuccess(list: List<T>, isRefreshingList: Boolean)

    fun onLoadPageError(isFirstPage: Boolean)

    fun onFinishList()

}