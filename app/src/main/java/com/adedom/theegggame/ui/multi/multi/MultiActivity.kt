package com.adedom.theegggame.ui.multi.multi

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : MapActivity() { // 5/8/62

    // TODO: 25/05/2562 toast name

    private lateinit var mViewModel: MultiActivityViewModel

    private var mRoomInfo = ArrayList<RoomInfo>()
    private var mMulti = ArrayList<Multi>()
    private var switchItem = GameSwitch.ON

    private var mTime: Int = FIFTEEN_MINUTE
    private var scoreTeamA = 0
    private var scoreTeamB = 0

    val playerId = MainActivity.sPlayerItem.playerId
    val room = RoomInfoActivity.sRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = MultiActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(MultiActivityViewModel::class.java)

        init()
    }

    private fun init() {
        toolbar.title = getString(R.string.multi_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTvTime.visibility = View.VISIBLE
        mTvRed.visibility = View.VISIBLE
        mTvBlue.visibility = View.VISIBLE
    }

    override fun gameLoop() {
        mTime -= 1

        rndItem()

        checkRadius()

        when {
            scoreTeamA + scoreTeamB >= 5 -> {
                finish()
                baseContext.toast("TEAM A = $scoreTeamA\nTEAM B = $scoreTeamB")
            }
            mTime == 0 -> {
                finish()
                baseContext.toast(R.string.time_out)
                baseContext.toast("TEAM A = $scoreTeamA\nTEAM B = $scoreTeamB")
            }
            else -> {
                mTvTime.text = mTime.toString()
                mTvRed.text = scoreTeamA.toString()
                mTvBlue.text = scoreTeamB.toString()
            }
        }
    }

    private fun rndItem() {
        if (mRoomInfo.size == 0) return
        if (room.status == HEAD && switchItem == GameSwitch.ON &&
            mRoomInfo[0].latitude != LATLNG_ZERO && mRoomInfo[0].longitude != LATLNG_ZERO
        ) {
            switchItem = GameSwitch.OFF
            for (i in 0 until NUMBER_OF_ITEM) insertMulti()
        } else if (room.status == TAIL && switchItem == GameSwitch.ON && mMulti.size != 0) {
            switchItem = GameSwitch.OFF
            mMulti.forEach { multi ->
                val distance = FloatArray(1)
                Location.distanceBetween(
                    sLatLng.latitude, sLatLng.longitude,
                    multi.latitude, multi.longitude, distance
                )

                if (distance[0] > THREE_KILOMETER) insertMulti()
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
                insertMultiCollection(multi.multi_id)
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

        setScore()

//        fightGame()
    }

    private fun setLatlng() {
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        mViewModel.setLatlng(room.room_no!!, playerId!!, lat, lng).observe(this, Observer {
            if (it.result == FAILED) baseContext.failed()
        })
    }

    private fun fetchRoomInfo() {
        mViewModel.getRoomInfo(room.room_no!!).observe(this, Observer {
            if (it != mRoomInfo) {
                mRoomInfo = it as ArrayList<RoomInfo>
                Player(mRoomInfo)
            }
        })
    }

    private fun fetchMulti() {
        mViewModel.getMulti(room.room_no!!).observe(this, Observer {
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
        mViewModel.insertMulti(roomNo!!, lat, lng).observe(this, Observer {
            if (it.result == FAILED) baseContext.failed()
        })
    }

    private fun insertMultiCollection(multiId: String) {
        val team = RoomInfoActivity.sTeam
        mViewModel.insertMultiCollection(multiId, room.room_no!!, playerId!!, team)
            .observe(this, Observer {
                if (it.result == COMPLETED) baseContext.toast(R.string.the_egg_game)
            })
    }

    private fun setScore() {
        mViewModel.getMultiScore(room.room_no!!).observe(this, Observer {
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
