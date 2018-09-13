package com.manuelperera.flightsschedules.presentation.selectairport

import android.view.View
import androidx.core.content.ContextCompat
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingAdapter
import kotlinx.android.synthetic.main.item_airport.view.*

class AirportAdapter(
        onItemClick: (Airport) -> Unit,
        onRetryClick: () -> Unit
) : PagingAdapter<Airport>(onRetryClick) {

    override var itemLayout = R.layout.item_airport

    override var onBindItem: (View, Airport) -> Unit = { itemView, airport ->
        with(itemView) {
            selected(itemView, airport.isSelected)
            airportNameTxt.text = airport.name
            airportCityTxt.text = airport.cityCode
            airportCountryTxt.text = airport.countryCode

            container.setOnClickListener { _ ->
                getItemsList().forEach { it.isSelected = false }
                airport.isSelected = true
                selected(itemView, airport.isSelected)
                onItemClick(airport)
                notifyDataSetChanged()
            }
        }
    }

    private fun selected(itemView: View, isSelected: Boolean) {
        itemView.container.background = if (isSelected) {
            ContextCompat.getDrawable(itemView.context, R.drawable.ripple_accent_bg)
        } else {
            ContextCompat.getDrawable(itemView.context, R.drawable.ripple_white_bg)
        }
    }

}