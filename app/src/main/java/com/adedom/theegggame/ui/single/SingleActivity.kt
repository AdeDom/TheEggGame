package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.networks.SingleApi
import com.adedom.theegggame.data.repositories.SingleRepository
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : MapActivity() {

    private lateinit var mViewModel: SingleActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = SingleActivityFactory(SingleRepository(SingleApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(SingleActivityViewModel::class.java)

        init()
    }

    private fun init() {
        toolbar.title = getString(R.string.single_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mFloatingActionButton.setOnClickListener {
            baseContext.completed()

            //todo backpack item
        }
    }

    override fun gameLoop() {
        if (switchItem == GameSwitch.ON) {
            switchItem = GameSwitch.OFF
            Item(single)
        }

        checkRadius()
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)
        setCamera(sGoogleMap, sLatLng)

        Player(sLatLng)

        rndItem(sLatLng)
    }

    private fun checkRadius() {
        single.forEachIndexed { index, item ->
            val distance = FloatArray(1)
            Location.distanceBetween(
                sLatLng.latitude,
                sLatLng.longitude,
                item.latitude,
                item.longitude,
                distance
            )

            if (distance[0] < ONE_HUNDRED_METER) {
                keepItem(index)
                markerItems[index].remove()
                single.removeAt(index)
                switchItem = GameSwitch.ON
                return
            }

            if (distance[0] > TWO_KILOMETER) {
                switchItem = GameSwitch.ON
                single.removeAt(index)
                return
            }
        }
    }

    private fun keepItem(index: Int) {
        val (myItem, values) = getItemValues(index, timeStamp)
        val playerId = this.getPrefLogin(PLAYER_ID)
        mViewModel.insertItem(playerId, myItem, values).observe(this, Observer {
            if (it.result == COMPLETED) baseContext.toast(detailItem(myItem, values))
        })
    }

}
