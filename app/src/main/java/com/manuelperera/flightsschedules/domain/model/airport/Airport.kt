package com.manuelperera.flightsschedules.domain.model.airport

import android.os.Parcel
import com.manuelperera.flightsschedules.domain.model.base.KParcelable
import com.manuelperera.flightsschedules.domain.model.base.parcelableCreator
import com.manuelperera.flightsschedules.domain.model.base.readBoolean
import com.manuelperera.flightsschedules.domain.model.base.writeBoolean
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingObject.ItemViewType.ITEM

class Airport(
        val airportCode: String,
        val latitude: Double,
        val longitude: Double,
        val cityCode: String,
        val countryCode: String,
        val locationType: String,
        val name: String,
        val utcOffset: Double,
        val timeZoneId: String,
        var isSelected: Boolean = false
) : PagingObject, KParcelable {

    override var itemViewType = ITEM

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Airport)
    }

    private constructor(p: Parcel) : this(
            airportCode = p.readString() ?: "",
            latitude = p.readDouble(),
            longitude = p.readDouble(),
            cityCode = p.readString() ?: "",
            countryCode = p.readString() ?: "",
            locationType = p.readString() ?: "",
            name = p.readString() ?: "",
            utcOffset = p.readDouble(),
            timeZoneId = p.readString() ?: "",
            isSelected = p.readBoolean()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(airportCode)
        writeDouble(latitude)
        writeDouble(longitude)
        writeString(cityCode)
        writeString(countryCode)
        writeString(locationType)
        writeString(name)
        writeDouble(utcOffset)
        writeString(timeZoneId)
        writeBoolean(isSelected)
    }

}