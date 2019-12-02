package com.adedom.theegggame.multi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.MultiItem
import com.adedom.theegggame.util.MapActivity
import com.adedom.theegggame.util.MyConnect
import com.adedom.theegggame.util.MyResultSet
import com.adedom.utility.rndLatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.sql.ResultSet

class Item { // 31/7/62

    constructor() {
        removeMarkerItem()
        feedItemMulti()
    }

    constructor(latLng: LatLng) {
        rndItem(latLng)
    }

    private fun removeMarkerItem() {
        if (MultiActivity.mMarkerItem != null) {
            for (marker in MultiActivity.mMarkerItem) {
                marker.remove()
            }
            MultiActivity.mMarkerItem.clear()
        }
    }

    private fun feedItemMulti() {
        MultiActivity.mMultiItem.clear()
        val sql = "SELECT * FROM tbl_multi\n" +
                "WHERE room_no = '${GetReadyActivity.mNoRoom.trim()}' AND status_id = '1'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                while (rs.next()) {
                    val item = MultiItem(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6)
                    )
                    MultiActivity.mMultiItem.add(item)
                }

                setMarker()
            }
        })
    }

    private fun setMarker() {
        var bmp: Bitmap?
        for (i in MultiActivity.mMultiItem.indices) {
            bmp = BitmapFactory.decodeResource(MapActivity.sContext.resources, R.drawable.the_egg_game)

            MultiActivity.mMarkerItem.add(
                MapActivity.sGoogleMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(MultiActivity.mMultiItem[i].latitude, MultiActivity.mMultiItem[i].longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        .title(MultiActivity.mMultiItem[i].latitude.toString())
                        .snippet(MultiActivity.mMultiItem[i].longitude.toString())
                )
            )
        }
    }

    private fun rndItem(latLng: LatLng) {
        for (i in 1..Commons.NUMBER_OF_ITEM) {
            val sql = "INSERT INTO tbl_multi (room_no, latitude, longitude, status_id) \n" +
                    "VALUES ('${GetReadyActivity.mNoRoom.trim()}', " +
                    "'${rndLatLng(latLng.latitude, Commons.TWO_HUNDRED_METER).toString().trim()}', " +
                    "'${rndLatLng(latLng.longitude, Commons.TWO_HUNDRED_METER).toString().trim()}', " +
                    "'1')"
            MyConnect.executeQuery(sql)
        }
    }
}