package com.adedom.theegggame.ui.multi.multi

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.data.KEY_DATE
import com.adedom.library.data.KEY_TIME
import com.adedom.library.extension.failed
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.library.util.getDateTime
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.sTime
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.scoreTeamA
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.scoreTeamB
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : MapActivity<MultiActivityViewModel>() { // TODO: 25/05/2562 toast name

    private var mRoomInfo = ArrayList<RoomInfo>()
    private var mMulti = ArrayList<Multi>()

    val playerId = MainActivity.sPlayer.playerId
    val room = RoomInfoActivity.sRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MultiActivityViewModel::class.java)

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        mTvTime.visibility = View.VISIBLE
        mTvRed.visibility = View.VISIBLE
        mTvBlue.visibility = View.VISIBLE
    }

    override fun gameLoop() {
        sTime -= 1

        rndMultiItem(room.status!!, mRoomInfo.size, mMulti.size, { insertMulti() }, {
            mMulti.forEach {
                distanceOver(sLatLng, LatLng(it.latitude, it.longitude), THREE_KILOMETER) {
                    insertMulti()
                }
            }
        })

        checkRadius()

        when {
            //todo dialog finish game && bonus team win
            scoreTeamA + scoreTeamB >= 5 -> {
                finish()
                baseContext.toast(R.string.end_game)
                baseContext.toast("TEAM A = $scoreTeamA\nTEAM B = $scoreTeamB")
            }
            sTime <= 0 -> {
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
        mMulti.forEachIndexed { index, multi ->
            val distance = FloatArray(1)
            Location.distanceBetween(
                sLatLng.latitude, sLatLng.longitude,
                multi.latitude, multi.longitude, distance
            )

            if (distance[0] < ONE_HUNDRED_METER) {
                keepItemMulti(multi.multi_id)
                mMulti.removeAt(index)
                Item(mMulti)
                return
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        setCamera(sGoogleMap, sLatLng)

        setLatlng()

        fetchRoomInfo()

        fetchMulti()

        fetchScore()

//        fightGame()
    }

    private fun setLatlng() {
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        viewModel.setLatlng(room.room_no!!, playerId!!, lat, lng).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo(room.room_no!!).observe(this, Observer {
            if (it != mRoomInfo) {
                mRoomInfo = it as ArrayList<RoomInfo>
                Player(mRoomInfo)
            }
        })
    }

    private fun fetchMulti() {
        viewModel.getMulti(room.room_no!!).observe(this, Observer {
            if (it != mMulti) {
                mMulti = it as ArrayList<Multi>
                Item(mMulti)
            }
        })
    }

    private fun insertMulti() {
        val roomNo = room.room_no
        val lat = rndLatLng(sLatLng.latitude)
        val lng = rndLatLng(sLatLng.longitude)
        viewModel.insertMulti(roomNo!!, lat, lng).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })
    }

    private fun keepItemMulti(multiId: String) {
        viewModel.keepItemMulti(
            multiId,
            room.room_no!!,
            playerId!!,
            team,
            sLatLng.latitude,
            sLatLng.longitude,
            getDateTime(KEY_DATE),
            getDateTime(KEY_TIME)
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
