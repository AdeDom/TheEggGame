package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.completed
import com.adedom.library.extension.getPrefFile
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.KEY_TIME
import com.adedom.library.util.getDateTime
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameSwitch
import com.adedom.theegggame.util.KEY_COMPLETED
import com.adedom.theegggame.util.KEY_PLAYER_ID
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : GoogleMapActivity(R.id.mapFragment, 5000) {

    private lateinit var viewModel: SingleActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        viewModel = ViewModelProviders.of(this).get(SingleActivityViewModel::class.java)

        init()

        viewModel.itemBonus = 0
    }

    override fun onResume() {
        super.onResume()
        viewModel.switchItem = GameSwitch.ON
    }

    override fun onPause() {
        super.onPause()
        viewModel.switchItem = GameSwitch.OFF
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.single_player), true)

        mFloatingActionButton.setOnClickListener {
            baseContext.completed()

            //todo backpack item
        }
    }

    override fun onActivityRunning() {
        if (viewModel.switchItem == GameSwitch.ON) {
            viewModel.switchItem = GameSwitch.OFF
            Item(viewModel.single)
        }

        viewModel.rndMultiItem(sLatLng)

        viewModel.rndItemBonus(sLatLng)

        viewModel.checkRadius(sLatLng) { keepItemSingle(it) }

    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

//        setCamera(15F, 12F)

        Player(sLatLng)
    }

    private fun keepItemSingle(index: Int) {
        val playerId = this.getPrefFile(KEY_PLAYER_ID)
        val (myItem, values) = viewModel.getItemValues(index)
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        val date = getDateTime(KEY_DATE)
        val time = getDateTime(KEY_TIME)
        viewModel.keepItemSingle(playerId, myItem, values, lat, lng, date, time)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) {
                    baseContext.toast(viewModel.detailItem(myItem, values))
                }
            })
    }

}
