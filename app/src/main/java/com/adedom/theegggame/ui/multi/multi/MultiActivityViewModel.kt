package com.adedom.theegggame.ui.multi.multi

import android.location.Location
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MultiActivityViewModel : BaseViewModel() {

    var switchItem = GameSwitch.ON
    var mRoomInfo = ArrayList<RoomInfo>()
    var mMulti = ArrayList<Multi>()

    fun setLatlng(roomNo: String, playerId: String, latitude: Double, longitude: Double) =
        multiRepository.setLatlng(roomNo, playerId, latitude, longitude)

    fun getRoomInfo(roomNo: String) = multiRepository.getRoomInfo(roomNo)

    fun getMulti(roomNo: String) = multiRepository.getMulti(roomNo)

    fun insertMulti(roomNo: String, latitude: Double, longitude: Double) =
        multiRepository.insertMulti(roomNo, latitude, longitude)

    fun keepItemMulti(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = multiRepository.insertMultiCollection(
        multiId,
        roomNo,
        playerId,
        team,
        latitude,
        longitude,
        date,
        time
    )

    fun getScore(roomNo: String) = multiRepository.getMultiScore(roomNo)

    fun distanceOver(latLng1: LatLng, latLng2: LatLng, distance: Float, over: () -> Unit) {
        val d = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude, latLng1.longitude,
            latLng2.latitude, latLng2.longitude, d
        )

        if (d[0] > distance) over.invoke()
    }

    fun rndMultiItem(
        status: String,
        roomInfoSize: Int,
        multiSize: Int,
        head: () -> Unit,
        tail: () -> Unit
    ) {
        when {
            status == HEAD && switchItem == GameSwitch.ON && roomInfoSize != 0 -> {
                switchItem = GameSwitch.OFF
                for (i in 0 until NUMBER_OF_ITEM) head.invoke()
            }
            status == TAIL && switchItem == GameSwitch.ON && multiSize != 0 -> {
                switchItem = GameSwitch.OFF
                tail.invoke()
            }
        }
    }

    fun rndLatLng(latLng: Double): Double {
        var rnd = Math.random() / 100 // < 0.01
        rnd += RADIUS_TWO_HUNDRED_METER / 100000 // 200 Meter
        val s = String.format("%.7f", rnd)
        var ll: Double = if ((0..1).random() == 0) latLng + s.toDouble() else latLng - s.toDouble()
        ll = String.format("%.7f", ll).toDouble()
        return ll
    }

    companion object {
        val markerPlayers by lazy { arrayListOf<Marker>() }
        val markerItems by lazy { arrayListOf<Marker>() }
        var sTime: Int = TIME_FIFTEEN_MINUTE
        var scoreTeamA = 0
        var scoreTeamB = 0

        fun setImageProfile(
            image: String?,
            gender: String?,
            male: () -> Unit,
            female: () -> Unit,
            loadImage: () -> Unit
        ) {
            when {
                image == KEY_EMPTY && gender == KEY_MALE -> male.invoke()
                image == KEY_EMPTY && gender == KEY_FEMALE -> female.invoke()
                image != KEY_EMPTY -> loadImage.invoke()
            }
        }
    }

}
