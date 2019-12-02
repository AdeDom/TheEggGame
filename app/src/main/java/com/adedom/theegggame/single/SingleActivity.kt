package com.adedom.theegggame.single

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.ChatItem
import com.adedom.theegggame.data.models.SingleItem
import com.adedom.theegggame.ui.activities.MainActivity
import com.adedom.theegggame.util.MyConnect
import com.adedom.theegggame.util.MyMap
import com.adedom.theegggame.util.MyMediaPlayer
import com.adedom.theegggame.util.MyResultSet
import com.adedom.utility.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

open class SingleActivity : MyMap(), Commons { // 21/7/62

    val TAG = "SingleActivity"

    companion object {
        val mSingleItem = arrayListOf<SingleItem>()
        val mMarkerMyItem = arrayListOf<Marker>()
        val mChatItem = arrayListOf<ChatItem>()
        var mMarkerMyLocation: Marker? = null
        var mMyCircle: Circle? = null
        var mIsRndItem = true
        var mSegmentIndex = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar()
        setEvents()
    }

    private fun setToolbar() {
        toolbar.title = "Single player"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setEvents() {
//        mFab.setOnClickListener {
//            ChatSingleDialog()
//                .show(supportFragmentManager, null)
//        }
        mImgEggI.setOnClickListener {
            baseContext.toast(R.string.can_not_use_single, Toast.LENGTH_LONG)
        }
        mImgEggII.setOnClickListener {
            baseContext.toast(R.string.can_not_use_single, Toast.LENGTH_LONG)
        }
        mImgEggIII.setOnClickListener {
            baseContext.toast(R.string.can_not_use_single, Toast.LENGTH_LONG)
        }
        mImgEggIV.setOnClickListener {
            baseContext.toast(R.string.can_not_use_single, Toast.LENGTH_LONG)
        }
        mImgEggV.setOnClickListener {
            baseContext.toast(R.string.can_not_use_single, Toast.LENGTH_LONG)
        }
    }

    override fun onLocationChanged(location: Location?) {
        val latLng = LatLng(location!!.latitude, location!!.longitude)
        setCamera(latLng)
        Player(latLng)
        Item(latLng)
        checkRadius(latLng)
    }

    private fun setCamera(latLng: LatLng) {
        if (mIsCamera) {
            mIsCamera = false
            val update = CameraUpdateFactory.newLatLngZoom(latLng, Commons.CAMERA_ZOOM)
            mGoogleMap!!.animateCamera(update)
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

            if (distance[0] < Commons.ONE_HUNDRED_METER) {
                MyMediaPlayer.getSound(baseContext, R.raw.keep)
                insertMyItem(mSingleItem[i].itemId)
                mMarkerMyItem[i].remove()
                mSingleItem.removeAt(i)
                return
            }

            if (distance[0] > Commons.TWO_KILOMETER) {
                mSingleItem.removeAt(i)
                return
            }
        }
    }

    private fun insertMyItem(item: Int) {
        var myItem = item // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

//        val timeNow = System.currentTimeMillis() / 1000
//        if (timeNow > MainActivity.sTimeStamp + Commons.FIFTEEN_MINUTE) { // Multiply 2
//            values *= 2
//        }

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

        val sql =
            "SELECT * FROM tbl_my_item WHERE player_id = '${MainActivity.sPlayerItem.playerId}' AND " +
                    "object_id = '${myItem.toString().trim()}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                if (rs.next()) {
                    var num = rs.getInt(4)
                    num += values
                    val sql = "UPDATE tbl_my_item SET\n" +
                            "qty = '${num.toString().trim()}'\n" +
                            "WHERE player_id = '${MainActivity.sPlayerItem.playerId}' AND " +
                            "object_id = '${myItem.toString().trim()}'"
                    MyConnect.executeQuery(sql)
                } else {
                    val sql = "INSERT INTO tbl_my_item (player_id, object_id, qty) \n" +
                            "VALUES ('${MainActivity.sPlayerItem.playerId}', " +
                            "'${myItem.toString().trim()}', " +
                            "'${values.toString().trim()}')"
                    MyConnect.executeQuery(sql)
                }
            }
        })

        chatList(myItem, values)
        baseContext.toast(detailItem(myItem, values))
    }

    private fun chatList(myItem: Int, values: Int) { // type "1" -> public
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = df.format(Calendar.getInstance().time)
        val chatItem = ChatItem(
            MainActivity.sPlayerItem.playerId!!,
            time.toString(),
            detailItem(myItem, values),
            MainActivity.sPlayerItem.image!!,
            "3"
        )
        mChatItem.add(chatItem)
    }

    private fun detailItem(itemId: Int, values: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point : $values"
            2 -> name = "Egg I" // egg false
            3 -> name = "Egg II" // hide egg + radius
            4 -> name = "Egg III" // stun
            5 -> name = "Egg IV" // angel
            6 -> name = "Egg V" // devil
        }
        return name
    }

    override fun onBackPressed() {
        onDialogBack()
    }

    private fun onDialogBack() {
        val builder = AlertDialog.Builder(this@SingleActivity)
        builder.setTitle("You want to go back to main menu?")
            .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
            .setNegativeButton(R.string.yes) { dialog, which -> finish() }.show()
    }

    override fun onResume() {
        super.onResume()
        MyMediaPlayer.getMusic(baseContext, R.raw.music_game)
    }

    override fun onPause() {
        super.onPause()
        MyMediaPlayer.music!!.stop()
    }
}
