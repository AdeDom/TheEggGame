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
import com.adedom.utility.FAILED
import com.adedom.utility.failed
import com.adedom.utility.setCamera
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : MapActivity(), Commons { // 5/8/62

    private lateinit var mViewModel: MultiActivityViewModel
    private var mRoomInfo = arrayListOf<RoomInfo>()
    private val mMarkerPlayer by lazy { arrayListOf<Marker>() }

    private var mTime: Int = 900
    private var scoreTeamA = 0
    private var scoreTeamB = 0

    val playerId = MainActivity.sPlayerItem.playerId
    val roomNo = RoomInfoActivity.sRoom.room_no

    private var mIsRndItem = true
    private var mIsDialogFightGame = true

    companion object {
        val mMultiItem = arrayListOf<Multi>()
        val mMarkerItem = arrayListOf<Marker>()
    }

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

        val score = scoreTeamA + scoreTeamB
        if (score >= 5) {
            finish()
        } else {
            mTvTime.text = mTime.toString()
            mTvRed.text = scoreTeamA.toString()
            mTvBlue.text = scoreTeamB.toString()
        }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        setCamera(sGoogleMap, sLatLng)

        setLatlng()

        fetchRoomInfo()

//        Item()
//        checkNewItem()
//        checkRadius()
//        fightGame()
//        setScore()
    }

    private fun setLatlng() {
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        mViewModel.setLatlng(roomNo!!, playerId!!, lat, lng).observe(this, Observer {
            if (it.result == FAILED) baseContext.failed()
        })
    }

    private fun fetchRoomInfo() {
        mViewModel.getRoomInfo(roomNo!!).observe(this, Observer {
            mRoomInfo = it as ArrayList<RoomInfo>
            Player(mRoomInfo, mMarkerPlayer)
        })
    }

//    private fun checkNewItem() {
//        if (mRoomInfo[0].playerId == playerId
//            && mRoomInfo[0].latitude != Commons.LATLNG_ZERO
//            && mRoomInfo[0].longitude != Commons.LATLNG_ZERO
//            && mIsRndItem
//        ) {
//            mIsRndItem = false
//            Item(mLatLng!!)
//        } else if (mRoomInfo[0].playerId != playerId
//            && mRoomInfo[0].latitude != Commons.LATLNG_ZERO
//            && mRoomInfo[0].longitude != Commons.LATLNG_ZERO
//            && mIsRndItem
//        ) {
//            mIsRndItem = false
//            for (i in mRoomInfo.indices) {
//                val distance = FloatArray(1)
//                Location.distanceBetween(
//                    mLatLng!!.latitude, mLatLng!!.longitude,
//                    mRoomInfo[i].latitude!!, mRoomInfo[i].longitude!!, distance
//                )
//
//                if (distance[0] > Commons.THREE_KILOMETER) {
//                    Item(mLatLng!!)
//                }
//            }
//        }
//    }
//
//    private fun checkRadius() {
//        for (i in mMultiItem.indices) {
//            val distance = FloatArray(1)
//            Location.distanceBetween(
//                mLatLng!!.latitude, mLatLng!!.longitude,
//                mMultiItem[i].latitude, mMultiItem[i].longitude, distance
//            )
//
//            if (distance[0] < Commons.ONE_HUNDRED_METER) {
//                // TODO: 25/05/2562 toast name
//
//                MyMediaPlayer.getSound(baseContext, R.raw.keep)
//
//                val sql =
//                    "UPDATE tbl_multi SET player_id = '${playerId}', " +
//                            "status_id = '0' WHERE id = '${mMultiItem[i].id.trim()}'"
//                MyConnect.executeQuery(sql)
//            }
//        }
//    }
//
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
//
//    private fun setScore() {
//        //todo clear player before
//
//        val sqlA = "SELECT COUNT(*) FROM tbl_room_info AS ri , tbl_multi AS mu\n" +
//                "WHERE ri.player_id = mu.player_id AND ri.team = 'A'"
//        MyConnect.executeQuery(sqlA, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                scoreTeamA = if (rs.next()) {
//                    rs.getString(1)
//                } else {
//                    "0"
//                }
//            }
//        })
//
//        val sqlB = "SELECT COUNT(*) FROM tbl_room_info AS ri , tbl_multi AS mu\n" +
//                "WHERE ri.player_id = mu.player_id AND ri.team = 'B'"
//        MyConnect.executeQuery(sqlB, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                scoreTeamB = if (rs.next()) {
//                    rs.getString(1)
//                } else {
//                    "0"
//                }
//            }
//        })
//    }

}
