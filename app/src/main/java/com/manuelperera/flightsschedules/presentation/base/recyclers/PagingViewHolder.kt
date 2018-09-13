package com.manuelperera.flightsschedules.presentation.base.recyclers

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.manuelperera.flightsschedules.R

abstract class PagingViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)

}

class NoActionViewHolder<in T>(val view: View) : PagingViewHolder<T>(view) {

    override fun bind(item: T) {}

}

class ErrorViewHolder<in T>(val view: View, val reloadAction: () -> Unit) : PagingViewHolder<T>(view) {

    override fun bind(item: T) {
        val retryButton = view.findViewById<Button?>(R.id.retryButton)
        retryButton?.setOnClickListener { reloadAction() }
    }

}

class ItemViewHolder<T : PagingObject>(itemView: View, var onBindItem: (View, T) -> Unit) : PagingViewHolder<T>(itemView) {

    override fun bind(item: T) {
        onBindItem(itemView, item)
    }

}