package com.manuelperera.flightsschedules.presentation.base.recyclers

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.setUpSwipeRefresh
import com.manuelperera.flightsschedules.presentation.base.BaseActivity
import com.manuelperera.flightsschedules.presentation.base.Presenter
import com.manuelperera.flightsschedules.presentation.base.View

abstract class PagingActivity<P : Presenter<V>, V : View, T : PagingObject> : BaseActivity<P, V>(), PagingView<T> {

    open lateinit var pagingAdapter: PagingAdapter<T>
    open lateinit var presenterRequest: (Int, Boolean) -> Unit

    private lateinit var scrollListener: ScrollListener
    private var recyclerView: RecyclerView? = null
    private var swipeRefresh: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        setUpPagingParameters()
    }

    abstract fun setUpPagingParameters()

    protected fun setUpRecycler() {
        recyclerView?.adapter = pagingAdapter
        setUpScrollListener()
        swipeRefresh?.setUpSwipeRefresh { resetScrollListener() }
        requestPage()
    }

    private fun setUpScrollListener() {
        recyclerView?.layoutManager?.let {
            scrollListener = ScrollListener(it) { page -> requestPage(page) }
            recyclerView?.addOnScrollListener(scrollListener)
        }
    }

    protected fun resetScrollListener() {
        scrollListener.reset()
        recyclerView?.clearOnScrollListeners()
        setUpScrollListener()
        requestPage(scrollListener.defaultPage, true)
    }

    protected fun requestPage(page: Int = scrollListener.page, isRefreshingList: Boolean = false) {
        pagingAdapter.addLoading(page == scrollListener.defaultPage)
        presenterRequest(page, isRefreshingList)
    }

    override fun onLoadPageSuccess(list: List<T>, isRefreshingList: Boolean) {
        if (isRefreshingList) pagingAdapter.clear()
        swipeRefresh?.isRefreshing = false
        pagingAdapter.addItemsToList(list)
    }

    override fun onLoadPageError(isFirstPage: Boolean) {
        swipeRefresh?.isRefreshing = false
        pagingAdapter.addError(isFirstPage)
    }

    override fun onFinishList() {
        swipeRefresh?.isRefreshing = false
        recyclerView?.clearOnScrollListeners()
        pagingAdapter.finishOfList()
    }

}