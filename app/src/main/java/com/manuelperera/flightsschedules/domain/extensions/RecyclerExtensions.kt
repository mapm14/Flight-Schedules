package com.manuelperera.flightsschedules.domain.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.EMPTY
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.FULL_ERROR
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.FULL_LOADING
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.ITEM
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.SMALL_ERROR
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.SMALL_LOADING

fun RecyclerView.setUpGridLayoutManager(list: List<PagingObject>, spanCount: Int = 4) {
    GridLayoutManager(context, spanCount).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                    when (list[position].itemViewType) {
                        FULL_LOADING, SMALL_LOADING, FULL_ERROR, SMALL_ERROR, EMPTY -> spanCount
                        ITEM -> 1
                    }
        }
        layoutManager = this
    }
}

fun SwipeRefreshLayout.setUpSwipeRefresh(onRefresh: () -> Unit) {
    setThemeColors()
    setOnRefreshListener {
        onRefresh()
    }
}

fun SwipeRefreshLayout.setThemeColors() {
    setColorSchemeResources(
            R.color.colorAccent,
            R.color.colorAccentDark,
            R.color.colorAccentUltraDark
    )
}