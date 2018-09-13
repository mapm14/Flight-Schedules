package com.manuelperera.flightsschedules.presentation.schedulelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.getParcelableParam
import com.manuelperera.flightsschedules.domain.extensions.gone
import com.manuelperera.flightsschedules.domain.extensions.visible
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.domain.model.schedule.Schedule
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingActivity
import com.manuelperera.flightsschedules.presentation.map.MapsActivity
import com.manuelperera.flightsschedules.presentation.selectairport.AirportListActivity
import kotlinx.android.synthetic.main.activity_schedule_list.*
import javax.inject.Inject

class ScheduleListActivity : PagingActivity<ScheduleListPresenter, ScheduleListView, Schedule>(), ScheduleListView {

    @Inject
    override lateinit var presenter: ScheduleListPresenter

    private val departureAirportRequestCode = 101
    private val arrivalAirportRequestCode = 102

    override var view: ScheduleListView = this
    override var activityLayout: Int = R.layout.activity_schedule_list

    companion object {
        fun getIntent(context: Context) = Intent(context, ScheduleListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab.setOnClickListener { presenter.checkAirportsAndGoToMap() }
        departureAirportTxt.setOnClickListener { goToSelectAirports(getString(R.string.departure), departureAirportRequestCode) }
        arrivalAirportTxt.setOnClickListener { goToSelectAirports(getString(R.string.arrival), arrivalAirportRequestCode) }
    }

    override fun setUpPagingParameters() {
        pagingAdapter = ScheduleAdapter(::goToScheduleDetail) { requestPage() }
        presenterRequest = presenter::getSchedulesPage
        setUpRecycler()
    }

    override fun goToMap(departureAirport: Airport, arrivalAirport: Airport) {
        startActivity(MapsActivity.getIntent(this, departureAirport, arrivalAirport))
    }

    override fun showErrorConfigurationImage(title: Int) {
        fab.gone()
        swipeRefresh.gone()
        messageContainer.visible()
        messageContainer.text = getString(title)
    }

    override fun hideErrorConfigurationImage() {
        fab.visible()
        swipeRefresh.visible()
        messageContainer.gone()
    }

    private fun goToScheduleDetail(@Suppress("UNUSED_PARAMETER") schedule: Schedule) {
        // TODO: Navigate to ScheduleDetail
    }

    private fun goToSelectAirports(title: String, requestCode: Int) {
        startActivityForResult(AirportListActivity.getIntent(this, title), requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                pagingAdapter.clear()
                val airport: Airport = it.getParcelableParam()
                when (requestCode) {
                    departureAirportRequestCode -> {
                        presenter.departureAirport = airport
                        departureAirportTxt.text = airport.name
                    }
                    arrivalAirportRequestCode -> {
                        presenter.arrivalAirport = airport
                        arrivalAirportTxt.text = airport.name
                    }
                }
                resetScrollListener()
            }
        }
    }

}