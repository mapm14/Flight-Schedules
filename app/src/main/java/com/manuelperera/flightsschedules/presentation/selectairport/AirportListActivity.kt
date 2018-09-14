package com.manuelperera.flightsschedules.presentation.selectairport

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.manuelperera.flightsschedules.R
import com.manuelperera.flightsschedules.domain.extensions.getSerializableParam
import com.manuelperera.flightsschedules.domain.extensions.setParcelableParam
import com.manuelperera.flightsschedules.domain.extensions.setSerializableParam
import com.manuelperera.flightsschedules.domain.extensions.visible
import com.manuelperera.flightsschedules.domain.model.airport.Airport
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingActivity
import com.manuelperera.flightsschedules.presentation.base.recyclers.PagingView
import kotlinx.android.synthetic.main.activity_airport_list.*
import javax.inject.Inject

class AirportListActivity : PagingActivity<AirportListPresenter, PagingView<Airport>, Airport>(), PagingView<Airport> {

    @Inject
    override lateinit var presenter: AirportListPresenter

    override var view: PagingView<Airport> = this
    override var activityLayout: Int = R.layout.activity_airport_list

    companion object {
        fun getIntent(context: Context, title: String) =
                Intent(context, AirportListActivity::class.java).apply {
                    setSerializableParam(title)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title: String = getSerializableParam()
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener { onFabClicked() }
    }

    override fun setUpPagingParameters() {
        pagingAdapter = AirportAdapter(::airportSelected) { requestPage() }
        presenterRequest = { page, _ -> presenter.getAirportsPage(page) }
        setUpRecycler()
    }

    override fun onLoadPageSuccess(list: List<Airport>, isRefreshingList: Boolean) {
        super.onLoadPageSuccess(list, isRefreshingList)
        fab.visible()
    }

    private fun airportSelected(airport: Airport) {
        presenter.selectedAirport = airport
    }

    private fun onFabClicked() {
        presenter.selectedAirport?.let {
            val intent = Intent(this, this::class.java).apply { setParcelableParam(it) }
            setResult(Activity.RESULT_OK, intent)
            finish()
        } ?: showSnackbarWithRes(R.string.select_airport)
    }

}