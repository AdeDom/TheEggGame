package com.adedom.theegggame.ui.multi.multi

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.setCamera
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*

class MultiActivity : MapActivity(), Commons { // 5/8/62

//todo     mHandlerCountdown.removeCallbacks(mRunnableCountdown)

    private lateinit var mViewModel: MultiActivityViewModel
//    private val mRoomInfoItem = arrayListOf<RoomInfo>()
//    private val mHandlerCountdown = Handler()
//    private var mLatLng: LatLng? = null
//    private var mIsRndItem = true
//    private var mIsFinishGame = true
//    private var mIsDialogFightGame = true
//    private var scoreTeamA = ""
//    private var scoreTeamB = ""

    companion object {
        val mMultiItem = arrayListOf<Multi>()
        val mMarkerItem = arrayListOf<Marker>()
        val mMarkerPlayer = arrayListOf<Marker>()
        var mCircle: Circle? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = MultiActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(MultiActivityViewModel::class.java)

        init()

//        mRunnableCountdown.run()
    }

    private fun init() {
        toolbar.title = getString(R.string.multi_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTvRed.visibility = View.VISIBLE
        mTvBlue.visibility = View.VISIBLE
        mTvTime.visibility = View.VISIBLE
    }

//    private val mRunnableCountdown = object : Runnable {
//        override fun run() {
//            if (mIsFinishGame) {
//                mIsFinishGame = false
//                mHandlerCountdown.postDelayed(this, Commons.FIFTEEN_MINUTES)
//            } else {
//                finishGame()
//            }
//        }
//    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        setCamera(sGoogleMap, sLatLng)

//        setLatlng()
//        feedRoomInfo()
//        checkRadius()
//        fightGame()
//        setScore()
//        checkFinishGame()
    }

//    private fun setLatlng() {
//        val sql = "UPDATE tbl_room_info SET \n" +
//                "latitude = '${mLatLng!!.latitude.toString().trim()}',\n" +
//                "longitude = '${mLatLng!!.longitude.toString().trim()}'\n" +
//                "WHERE player_id = '${MainActivity.sPlayerItem.playerId!!.trim()}'"
//        MyConnect.executeQuery(sql)
//    }
//
//    private fun feedRoomInfo() {
//        mRoomInfoItem.clear()
//        val sql =
//            "SELECT ri.room_no,p.id,p.name,p.image,p.level,ri.latitude,ri.longitude,ri.team,ri.status_id\n" +
//                    "FROM tbl_room_info AS ri , tbl_player AS p\n" +
//                    "WHERE ri.room_no = '${RoomInfoActivity.sRoom.room_no!!.trim()}' AND ri.player_id = p.id\n" +
//                    "ORDER BY ri.id ASC"
//        MyConnect.executeQuery(sql, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                while (rs.next()) {
//                    val item = RoomInfo(
//                        rs.getString(1),
//                        rs.getDouble(2),
//                        rs.getDouble(3),
//                        rs.getString(4),
//                        rs.getString(5),
//                        rs.getString(6),
//                        rs.getString(7),
//                        rs.getInt(8),
//                        rs.getString(9)
//                    )
//                    mRoomInfoItem.add(item)
//                }
//
//                Player(mRoomInfoItem)
//                Item()
//                checkNewItem()
//            }
//        })
//    }
//
//    private fun checkNewItem() {
//        if (mRoomInfoItem[0].playerId == MainActivity.sPlayerItem.playerId
//            && mRoomInfoItem[0].latitude != Commons.LATLNG_ZERO
//            && mRoomInfoItem[0].longitude != Commons.LATLNG_ZERO
//            && mIsRndItem
//        ) {
//            mIsRndItem = false
//            Item(mLatLng!!)
//        } else if (mRoomInfoItem[0].playerId != MainActivity.sPlayerItem.playerId
//            && mRoomInfoItem[0].latitude != Commons.LATLNG_ZERO
//            && mRoomInfoItem[0].longitude != Commons.LATLNG_ZERO
//            && mIsRndItem
//        ) {
//            mIsRndItem = false
//            for (i in mRoomInfoItem.indices) {
//                val distance = FloatArray(1)
//                Location.distanceBetween(
//                    mLatLng!!.latitude, mLatLng!!.longitude,
//                    mRoomInfoItem[i].latitude!!, mRoomInfoItem[i].longitude!!, distance
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
//                    "UPDATE tbl_multi SET player_id = '${MainActivity.sPlayerItem.playerId!!.trim()}', " +
//                            "status_id = '0' WHERE id = '${mMultiItem[i].id.trim()}'"
//                MyConnect.executeQuery(sql)
//            }
//        }
//    }
//
//    private fun fightGame() {
//        //todo check fighting game
//
//        for (i in mRoomInfoItem.indices) {
//            if (mRoomInfoItem[i].playerId != MainActivity.sPlayerItem.playerId
//                && mRoomInfoItem[i].latitude != Commons.LATLNG_ZERO
//                && mRoomInfoItem[i].longitude != Commons.LATLNG_ZERO
//            ) {
//                val distance = FloatArray(1)
//                Location.distanceBetween(
//                    mLatLng!!.latitude, mLatLng!!.longitude,
//                    mRoomInfoItem[i].latitude!!, mRoomInfoItem[i].longitude!!, distance
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
//                        if (item.player_id == mRoomInfoItem[i].playerId) {
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
//                mTvRed.text = scoreTeamA
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
//                mTvBlue.text = scoreTeamB
//            }
//        })
//    }
//
//    private fun checkFinishGame() {
//        val numA = scoreTeamA.trim().toInt()
//        val numB = scoreTeamB.trim().toInt()
//        val total = numA + numB
//        if (total >= 5) {
//            finishGame()
//        }
//    }
//
//    private fun finishGame() {
//        mHandlerCountdown.removeCallbacks(mRunnableCountdown)
//
//        val sql =
//            "DELETE FROM tbl_multi WHERE room_no = '${RoomInfoActivity.sRoom.room_no.toString()}'"
//        MyConnect.executeQuery(sql)
//
//        val intent = Intent()
//        intent.putExtra("values1", scoreTeamA.trim())
//        intent.putExtra("values2", scoreTeamB.trim())
//        setResult(RESULT_OK, intent)
//        finish()
//    }
}
