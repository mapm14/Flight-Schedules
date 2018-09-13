package com.manuelperera.flightsschedules.presentation.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.getParcelableParam
import com.manuelperera.flightsschedules.domain.extensions.setParcelableParam
import com.manuelperera.flightsschedules.domain.model.airport.Airport

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    companion object {
        private const val DEPARTURE_AIRPORT = "DEPARTURE_AIRPORT"
        private const val ARRIVAL_AIRPORT = "ARRIVAL_AIRPORT"

        fun getIntent(context: Context, departure: Airport, arrival: Airport) =
                Intent(context, MapsActivity::class.java).apply {
                    setParcelableParam(departure, DEPARTURE_AIRPORT)
                    setParcelableParam(arrival, ARRIVAL_AIRPORT)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val departureAirport: Airport = intent.getParcelableParam(DEPARTURE_AIRPORT)
        val arrivalAirport: Airport = intent.getParcelableParam(ARRIVAL_AIRPORT)

        val departureLatLng = LatLng(departureAirport.latitude, departureAirport.longitude)
        googleMap.addMarker(MarkerOptions().position(departureLatLng).title(departureAirport.name))

        val arrivalLatLng = LatLng(arrivalAirport.latitude, arrivalAirport.longitude)
        googleMap.addMarker(MarkerOptions().position(arrivalLatLng).title(arrivalAirport.name))

        googleMap.addPolyline(PolylineOptions()
                .add(departureLatLng, arrivalLatLng)
                .width(5f)
                .color(R.color.colorPrimary))

        val latLngBounds = LatLngBounds.Builder()
                .include(departureLatLng)
                .include(arrivalLatLng)
                .build()

        val zoomPadding = 200
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, zoomPadding))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}