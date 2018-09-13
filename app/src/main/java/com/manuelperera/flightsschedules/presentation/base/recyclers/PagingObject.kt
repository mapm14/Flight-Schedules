package com.manuelperera.flightsschedules.presentation.base.recyclers

interface PagingObject {

    var itemViewType: ItemViewType

    enum class ItemViewType {
        FULL_LOADING,
        SMALL_LOADING,
        FULL_ERROR,
        SMALL_ERROR,
        ITEM,
        EMPTY
    }

}