package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.adedom.utility.extension.completed
import com.adedom.utility.extension.getPrefLogin
import com.adedom.utility.extension.setToolbar
import com.adedom.utility.extension.toast
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : MapActivity<SingleActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SingleActivityViewModel::class.java)

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.single_player))

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

        rndMultiItem(sLatLng)

        checkRadius(sLatLng) { keepItem(it) }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)
        setCamera(sGoogleMap, sLatLng)

        Player(sLatLng)
    }

    private fun keepItem(index: Int) {
        val playerId = this.getPrefLogin(PLAYER_ID)
        val (myItem, values) = getItemValues(index, timeStamp)
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        val date = getDateTime(DATE)
        val time = getDateTime(TIME)
        viewModel.insertItem(playerId, myItem, values, lat, lng, date, time)
            .observe(this, Observer {
                if (it.result == COMPLETED) baseContext.toast(detailItem(myItem, values))
            })
    }

}
