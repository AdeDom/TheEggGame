package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.pauseMusic
import com.adedom.theegggame.R
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playMusicGame
import com.adedom.theegggame.util.extension.setSoundMusic
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : GoogleMapActivity(R.id.mapFragment, 5000) {

    private lateinit var viewModel: SingleActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        viewModel = ViewModelProviders.of(this).get(SingleActivityViewModel::class.java)

        init()

    }

    override fun onResume() {
        super.onResume()
        viewModel.switchItem = GameSwitch.ON

        sContext.playMusicGame()
    }

    override fun onPause() {
        super.onPause()
        viewModel.switchItem = GameSwitch.OFF

        pauseMusic()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_sound_music) sContext.setSoundMusic()
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.single_player), true)

        viewModel.itemBonus = 0

        mFloatingActionButton.setOnClickListener {
            BackpackDialog().show(supportFragmentManager, null)
        }
    }

    override fun onActivityRunning() {

        viewModel.checkItem { single, markerItems -> Item(single, markerItems) }

        viewModel.rndMultiItem()

        viewModel.rndItemBonus()

        viewModel.checkRadius { keepItemSingle(it) }

        viewModel.createBot { Bot() }

    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        sContext.setLocality(mTvLocality, sLatLng)

        Player()

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        sGoogleMap!!.setMarkerConstant(druBkk, druIcon, DRU_TITLE, DRU_SNIPPET)
        sGoogleMap!!.setMarkerConstant(druSp, druIcon, DRU_TITLE, DRU_SNIPPET)
    }

    private fun keepItemSingle(index: Int) {
        val playerId = this.readPrefFile(KEY_PLAYER_ID)
        val (myItem, values) = viewModel.getItemValues(index)
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        viewModel.keepItemSingle(playerId, myItem, values, lat, lng).observe(this, Observer {
            if (it.result == KEY_COMPLETED) sContext.toast(viewModel.itemMessages(myItem, values))
        })
    }

}
