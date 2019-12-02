package com.adedom.theegggame.single

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.SingleItem
import com.adedom.theegggame.ui.activities.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : MapActivity() { // 21/7/62

    val TAG = "SingleActivity"
    private var mSwitchItem = GameSwitch.ON
    private val mSingleItem by lazy { arrayListOf<SingleItem>() }
    private val mMarkerMyItem by lazy { arrayListOf<Marker>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = "Single player"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mFloatingActionButton.setOnClickListener {
            baseContext.completed()
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
                val item = SingleItem(
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
                insertMyItem(mSingleItem[i].itemId)
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

    private fun insertMyItem(item: Int) {
        var myItem = item // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeNow = System.currentTimeMillis() / 1000
        if (timeNow > MainActivity.sTimeStamp + FIFTEEN_MINUTE) { // Multiply 2
            values *= 2
        }

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

//        val sql =
//            "SELECT * FROM tbl_my_item WHERE player_id = '${MainActivity.sPlayerItem.playerId}' AND " +
//                    "object_id = '${myItem.toString().trim()}'"
//        MyConnect.executeQuery(sql, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                if (rs.next()) {
//                    var num = rs.getInt(4)
//                    num += values
//                    val sql = "UPDATE tbl_my_item SET\n" +
//                            "qty = '${num.toString().trim()}'\n" +
//                            "WHERE player_id = '${MainActivity.sPlayerItem.playerId}' AND " +
//                            "object_id = '${myItem.toString().trim()}'"
//                    MyConnect.executeQuery(sql)
//                } else {
//                    val sql = "INSERT INTO tbl_my_item (player_id, object_id, qty) \n" +
//                            "VALUES ('${MainActivity.sPlayerItem.playerId}', " +
//                            "'${myItem.toString().trim()}', " +
//                            "'${values.toString().trim()}')"
//                    MyConnect.executeQuery(sql)
//                }
//            }
//        })

        baseContext.toast(detailItem(myItem, values))
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@SingleActivity)
        builder.setTitle("You want to go back to main menu?")
            .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
            .setNegativeButton(R.string.yes) { dialog, which -> finish() }.show()
    }

}
