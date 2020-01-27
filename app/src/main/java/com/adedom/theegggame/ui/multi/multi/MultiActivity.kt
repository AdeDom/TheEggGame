package com.adedom.theegggame.ui.multi.multi

import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.pauseMusic
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playMusicGame
import com.adedom.theegggame.util.extension.setSoundMusic
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : GoogleMapActivity(R.id.mapFragment, 5000) { // TODO: 25/05/2562 toast name

    private lateinit var viewModel: MultiActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        viewModel = ViewModelProviders.of(this).get(MultiActivityViewModel::class.java)

        viewModel.room = intent.getParcelableExtra(ROOM) as Room
        viewModel.team = intent.getStringExtra(TEAM) as String

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
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_sound_music) {
            sContext.setSoundMusic()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        viewModel.time = TIME_FIFTEEN_MINUTE

        mTvTime.visibility = View.VISIBLE
        mTvRed.visibility = View.VISIBLE
        mTvBlue.visibility = View.VISIBLE
    }

    override fun onActivityRunning() {
        viewModel.time -= 1

        viewModel.rndMultiItem { insertMulti() }

        viewModel.checkRadius { multi_id, multiItems, markerItems ->
            keepItemMulti(multi_id)
            Item(multiItems, markerItems)
        }

        viewModel.checkEndGame({ scoreTeamA, scoreTeamB ->
            finish()
            sContext.toast(R.string.end_game)
            sContext.toast("TEAM A = $scoreTeamA \nTEAM B = $scoreTeamB ")
        }, { scoreTeamA, scoreTeamB ->
            val bundle = Bundle()
            bundle.putString(TEAM_A, scoreTeamA.toString())
            bundle.putString(TEAM_B, scoreTeamB.toString())

            val dialog = EndGameDialog()
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, null)
        }, { scoreTeamA, scoreTeamB, time ->
            mTvTime.text = time.toString()
            mTvRed.text = scoreTeamA.toString()
            mTvBlue.text = scoreTeamB.toString()
        })
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        sContext.setLocality(mTvLocality, sLatLng)

        setLatlng()

        fetchRoomInfo()

        fetchMulti()

        fetchScore()

//        fightGame()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        sGoogleMap!!.setMarkerConstant(druBkk, druIcon, DRU_TITLE, DRU_SNIPPET)
        sGoogleMap!!.setMarkerConstant(druSp, druIcon, DRU_TITLE, DRU_SNIPPET)
    }

    private fun setLatlng() {
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        viewModel.setLatlng(MainActivity.sPlayer.playerId!!, lat, lng).observe(this, Observer {
            if (it.result == KEY_FAILED) sContext.failed()
        })
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo().observe(this, Observer {
            if (it != viewModel.roomInfoItems) {
                viewModel.roomInfoItems = it as ArrayList<RoomInfo>
                Player(sContext, sGoogleMap, viewModel.roomInfoItems, viewModel.markerPlayers)
            }
        })
    }

    private fun fetchMulti() {
        viewModel.getMulti().observe(this, Observer {
            if (it != viewModel.multiItems) {
                viewModel.multiItems = it as ArrayList<Multi>
                Item(viewModel.multiItems, viewModel.markerItems)
            }
        })
    }

    private fun insertMulti() {
        viewModel.insertMulti(sLatLng).observe(this, Observer {
            if (it.result == KEY_FAILED) sContext.failed()
        })
    }

    private fun keepItemMulti(multiId: String) {
        viewModel.keepItemMulti(
            multiId,
            MainActivity.sPlayer.playerId,
            sLatLng.latitude,
            sLatLng.longitude
        ).observe(this, Observer {
            if (it.result == KEY_COMPLETED) sContext.toast(R.string.the_egg_game)
        })
    }

    private fun fetchScore() {
        viewModel.getScore().observe(this, Observer {
            viewModel.scoreTeamA = it.teamA
            viewModel.scoreTeamB = it.teamB
        })
    }

    //region fightGame
//    private fun fightGame() {
//        //todo check fighting game
//
//        for (i in mRoomInfo.indices) {
//            if (mRoomInfo[i].playerId != playerId
//                && mRoomInfo[i].latitude != Commons.LATLNG_ZERO
//                && mRoomInfo[i].longitude != Commons.LATLNG_ZERO
//            ) {
//                val distance = FloatArray(1)
//                Location.distanceBetween(
//                    mLatLng!!.latitude, mLatLng!!.longitude,
//                    mRoomInfo[i].latitude!!, mRoomInfo[i].longitude!!, distance
//                )
//
//                if (distance[0] > Commons.ONE_FIFTY_HUNDRED_METER) {
//                    // TODO: 25/05/2562 people > 2
//                    mIsDialogFightGame = true
//                }
//
//                // TODO: 25/05/2562 check star is where
//                if (distance[0] < Commons.ONE_HUNDRED_METER && mIsDialogFightGame) {
//                    mIsDialogFightGame = false
//
//                    for (item in mMultiItem) {
//                        if (item.player_id == mRoomInfo[i].playerId) {
//                            val dialog =
//                                FightGameDialog()
//                            dialog.show(supportFragmentManager, null)
//                            dialog.isCancelable = false
//                        }
//                    }
//                }
//            }
//        }
//    }
    //endregion
}
