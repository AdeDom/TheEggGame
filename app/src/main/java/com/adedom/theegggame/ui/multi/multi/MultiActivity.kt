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
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.sTime
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.scoreTeamA
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.scoreTeamB
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivityViewModel
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playMusicGame
import com.adedom.theegggame.util.extension.setSoundMusic
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : GoogleMapActivity(R.id.mapFragment, 5000) { // TODO: 25/05/2562 toast name

    private lateinit var viewModel: MultiActivityViewModel

    private val room = RoomInfoActivityViewModel.sRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        viewModel = ViewModelProviders.of(this).get(MultiActivityViewModel::class.java)

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

        mTvTime.visibility = View.VISIBLE
        mTvRed.visibility = View.VISIBLE
        mTvBlue.visibility = View.VISIBLE
    }

    override fun onActivityRunning() {
        sTime -= 1

        viewModel.rndMultiItem(
            room.status!!,
            viewModel.mRoomInfo.size,
            viewModel.mMulti.size,
            { insertMulti() }, {
                viewModel.mMulti.forEach {
                    viewModel.distanceOver(
                        sLatLng,
                        LatLng(it.latitude, it.longitude),
                        RADIUS_THREE_KILOMETER
                    ) {
                        insertMulti()
                    }
                }
            })

        checkRadius()

        when {
            //todo dialog finish game && bonus team win
            scoreTeamA + scoreTeamB >= 5 -> {
                viewModel.mission()

                finish()
                baseContext.toast(R.string.end_game)
                baseContext.toast("TEAM A = $scoreTeamA\nTEAM B = $scoreTeamB")
            }
            sTime <= 0 -> {
                viewModel.mission()

                val bundle = Bundle()
                bundle.putString(TEAM_A, scoreTeamA.toString())
                bundle.putString(TEAM_B, scoreTeamB.toString())

                val dialog = EndGameDialog()
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, null)
            }
            else -> {
                mTvTime.text = sTime.toString()
                mTvRed.text = scoreTeamA.toString()
                mTvBlue.text = scoreTeamB.toString()
            }
        }
    }

    private fun checkRadius() {
        viewModel.mMulti.forEachIndexed { index, multi ->
            val distance = FloatArray(1)
            Location.distanceBetween(
                sLatLng.latitude, sLatLng.longitude,
                multi.latitude, multi.longitude, distance
            )

            if (distance[0] < RADIUS_ONE_HUNDRED_METER) {
                keepItemMulti(multi.multi_id)
                viewModel.mMulti.removeAt(index)
                Item(viewModel.mMulti)
                return
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        baseContext.setLocality(mTvLocality, sLatLng)

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
        viewModel.setLatlng(room.room_no!!, MainActivity.sPlayer.playerId!!, lat, lng).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo(room.room_no!!).observe(this, Observer {
            if (it != viewModel.mRoomInfo) {
                viewModel.mRoomInfo = it as ArrayList<RoomInfo>
                Player(viewModel.mRoomInfo)
            }
        })
    }

    private fun fetchMulti() {
        viewModel.getMulti(room.room_no!!).observe(this, Observer {
            if (it != viewModel.mMulti) {
                viewModel.mMulti = it as ArrayList<Multi>
                Item(viewModel.mMulti)
            }
        })
    }

    private fun insertMulti() {
        val roomNo = room.room_no
        val lat = viewModel.rndLatLng(sLatLng.latitude)
        val lng = viewModel.rndLatLng(sLatLng.longitude)
        viewModel.insertMulti(roomNo!!, lat, lng).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })
    }

    private fun keepItemMulti(multiId: String) {
        viewModel.keepItemMulti(
            multiId,
            room.room_no!!,
            MainActivity.sPlayer.playerId,
            RoomInfoActivityViewModel.team,
            sLatLng.latitude,
            sLatLng.longitude
        ).observe(this, Observer {
            if (it.result == KEY_COMPLETED) baseContext.toast(R.string.the_egg_game)
        })
    }

    private fun fetchScore() {
        viewModel.getScore(room.room_no!!).observe(this, Observer {
            scoreTeamA = it.teamA
            scoreTeamB = it.teamB
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
