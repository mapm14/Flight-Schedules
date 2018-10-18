package com.manuelperera.flightsschedules.presentation.base.recyclers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.inflate
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.EMPTY
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.FULL_ERROR
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.FULL_LOADING
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.ITEM
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.SMALL_ERROR
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.SMALL_LOADING

abstract class PagingAdapter<T : PagingObject>(
        private val onRetryClick: () -> Unit
) : RecyclerView.Adapter<PagingViewHolder<T>>() {

    protected abstract var onBindItem: (View, T) -> Unit
    protected abstract var itemLayout: Int

    protected open var itemFullLoadingLayout = R.layout.item_full_loading
    protected open var itemSmallLoadingLayout = R.layout.item_small_loading
    protected open var itemFullErrorLayout = R.layout.item_full_error
    protected open var itemSmallErrorLayout = R.layout.item_small_error
    protected open var itemEmptyLayout = R.layout.item_empty

    var list = mutableListOf<T>()
        private set

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun clear() {
        recyclerView?.post {
            list.clear()
            notifyDataSetChanged()
        }
    }

    fun finishOfList() {
        val listHasNoItems = list.any { it.itemViewType == ITEM }.not()
        if (listHasNoItems) {
            addItem(EMPTY)
        } else {
            recyclerView?.post {
                val listHasErrorsOrLoading = list.any { it.itemViewType != ITEM }
                list.retainAll { it.itemViewType == ITEM }
                if (listHasErrorsOrLoading) {
                    notifyItemRemoved(list.size)
                }
            }
        }
    }

    fun addItemsToList(newList: List<T>) {
        addItem(ITEM, newList = newList)
    }

    fun addLoading(isFull: Boolean) {
        addItem(if (isFull) FULL_LOADING else SMALL_LOADING)
    }

    fun addError(isFull: Boolean) {
        addItem(if (isFull) FULL_ERROR else SMALL_ERROR)
    }

    @Synchronized
    @Suppress("UNCHECKED_CAST")
    private fun addItem(type: PagingObject.ItemViewType, newList: List<T>? = null) {
        recyclerView?.post {
            val listHasErrorsOrLoading = list.any { it.itemViewType != ITEM }

            list.retainAll { it.itemViewType == ITEM }

            when (type) {
                FULL_LOADING, SMALL_LOADING, FULL_ERROR, SMALL_ERROR, EMPTY -> {
                    if (listHasErrorsOrLoading) {
                        notifyItemRemoved(list.size)
                    }

                    val pagingObject = PagingObjectImpl().apply { itemViewType = type }
                    list.add(pagingObject as T)

                    notifyItemInserted(list.indexOf(pagingObject))
                }
                ITEM -> {
                    notifyItemRemoved(list.size)

                    newList?.let {
                        val newListSize = list.size + newList.size
                        list.addAll(it)

                        for (i in (list.size - 1)..(newListSize)) {
                            notifyItemInserted(i)
                        }
                    }
                }
            }
        }
    }

    protected fun getItemsList(): List<T> = list.filter { it.itemViewType == ITEM }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder<T> =
            when (viewType) {
                FULL_LOADING.ordinal -> NoActionViewHolder(parent.inflate(itemFullLoadingLayout))
                SMALL_LOADING.ordinal -> NoActionViewHolder(parent.inflate(itemSmallLoadingLayout))
                FULL_ERROR.ordinal -> ErrorViewHolder(parent.inflate(itemFullErrorLayout), onRetryClick)
                SMALL_ERROR.ordinal -> ErrorViewHolder(parent.inflate(itemSmallErrorLayout), onRetryClick)
                ITEM.ordinal -> ItemViewHolder(parent.inflate(itemLayout), onBindItem)
                EMPTY.ordinal -> NoActionViewHolder(parent.inflate(itemEmptyLayout))
                else -> ErrorViewHolder(parent.inflate(itemSmallErrorLayout), onRetryClick)
            }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PagingViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int) = list[position].itemViewType.ordinal

}