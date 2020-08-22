package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.setMarkerConstant
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
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

        viewModel = ViewModelProvider(this).get(SingleActivityViewModel::class.java)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sound_music) sContext.setSoundMusic()
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

        viewModel.bot { Bot(it) }

    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        //TODO issues location -> LatLng(0.0,0.0)
//        sContext.setLocality(mTvLocality, sLatLng)

        Player()

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        sGoogleMap!!.setMarkerConstant(
            druBkk,
            druIcon,
            getString(R.string.dru_title),
            getString(R.string.dru_snippet)
        )
        sGoogleMap!!.setMarkerConstant(
            druSp,
            druIcon,
            getString(R.string.dru_title),
            getString(R.string.dru_snippet)
        )
    }

    private fun keepItemSingle(index: Int) {
        //TODO keep item no double && i wants keep one only

        val playerId = this.readPrefFile(KEY_PLAYER_ID)
        val (myItem, values) = viewModel.getItemValues(index)
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        viewModel.keepItemSingle(playerId, myItem, values, lat, lng).observe(this, Observer {
            if (it.result == KEY_COMPLETED) sContext.toast(viewModel.itemMessages(myItem, values))
        })
    }

}
