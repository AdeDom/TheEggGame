package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.data.networks.SingleApi
import com.adedom.theegggame.data.repositories.SingleRepository
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : MapActivity() { // 2/12/19

    private lateinit var mViewModel: SingleActivityViewModel
    private var mSwitchItem = GameSwitch.ON
    private val mSingleItem by lazy { arrayListOf<Single>() }
    private val mMarkerMyItem by lazy { arrayListOf<Marker>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = SingleActivityFactory(
            SingleRepository(SingleApi())
        )
        mViewModel = ViewModelProviders.of(this, factory).get(SingleActivityViewModel::class.java)

        toolbar.title = getString(R.string.single_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mFloatingActionButton.setOnClickListener {
            baseContext.completed()

            //todo backpack item
        }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        setCamera(sGoogleMap, sLatLng)

        Player(sLatLng)

        rndItem(sLatLng)

        if (mSwitchItem == GameSwitch.ON) {
            mSwitchItem = GameSwitch.OFF
            Item(mSingleItem, mMarkerMyItem)
        }

        checkRadius(sLatLng)
    }

    private fun rndItem(latLng: LatLng) {
        if (mSingleItem.size < ITEM_MIN) {
            val numItem = (ITEM_MIN..MAX_ITEM).random()
            for (i in 0 until numItem) {
                val item = Single(
                    (1..3).random(),
                    rndLatLng(latLng.latitude, TWO_HUNDRED_METER),
                    rndLatLng(latLng.longitude, TWO_HUNDRED_METER)
                )
                mSingleItem.add(item)
            }
        }
    }

    private fun checkRadius(latLng: LatLng) {
        for (i in 0 until mSingleItem.size) {
            val distance = FloatArray(1)
            Location.distanceBetween(
                latLng.latitude,
                latLng.longitude,
                mSingleItem[i].latitude,
                mSingleItem[i].longitude,
                distance
            )

            if (distance[0] < ONE_HUNDRED_METER) {
                keepItem(mSingleItem[i].itemId)
                mMarkerMyItem[i].remove()
                mSingleItem.removeAt(i)
                mSwitchItem = GameSwitch.ON
                return
            }

            if (distance[0] > TWO_KILOMETER) {
                mSwitchItem = GameSwitch.ON
                mSingleItem.removeAt(i)
                return
            }
        }
    }

    private fun keepItem(item: Int) {
        var myItem = item // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeNow = System.currentTimeMillis() / 1000
        if (timeNow > MainActivity.sTimeStamp + FIFTEEN_MINUTE) values *= 2 // Multiply 2

        when (myItem) {
            2 -> { // mystery box
                myItem = (1..2).random() // random exp and item*/
                values += (1..20).random() // mystery box + 20 point.
                if (myItem == 2) {
                    myItem = (2..6).random() // random item
                    values = 1
                }
            }
            3 -> { // item
                myItem = (2..6).random()
                values = 1
            }
        }

        val playerId = this.getPrefLogin(PLAYER_ID)
        mViewModel.insertItem(playerId, myItem, values).observe(this, Observer {
            if (it.result == COMPLETED) baseContext.toast(detailItem(myItem, values))
        })
    }

}
