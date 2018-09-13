package com.manuelperera.flightsschedules.presentation.base.recyclers

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ScrollListener(
        private val layoutManager: RecyclerView.LayoutManager,
        private val onPageIncrement: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private val visibleThreshold = 2
    var defaultPage = 0
        private set
    var page = defaultPage
        private set
    private var previousTotalItemCount = 0
    private var isLoading = true
    private val minTotalItemCount = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = when (layoutManager) {
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> getLastVisibleItem(layoutManager.findLastVisibleItemPositions(null))
            else -> throw IllegalArgumentException("ScrollListener only supports GridLayoutManager, LinearLayoutManager or StaggeredGridLayoutManager instances")
        }

        if (totalItemCount < previousTotalItemCount) {
            page = defaultPage
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) isLoading = true
        }

        if (isLoading && (totalItemCount > previousTotalItemCount) && (totalItemCount - previousTotalItemCount > minTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            isLoading = true
            page++
            onPageIncrement(page)
        }
    }

    fun reset() {
        page = defaultPage
        previousTotalItemCount = 0
        isLoading = true
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

}