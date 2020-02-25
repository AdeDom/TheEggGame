package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.library.util.distanceBetween
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playSoundKeep
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MultiActivityViewModel : BaseViewModel() {

    val markerPlayers by lazy { arrayListOf<Marker>() }
    val markerItems by lazy { arrayListOf<Marker>() }
    var roomInfoItems = ArrayList<RoomInfo>()
    var multiItems = ArrayList<Multi>()
    var switchItem = GameSwitch.ON
    var time: Int = 0
    var scoreTeamA: Int = 0
    var scoreTeamB: Int = 0

    lateinit var room: Room
    lateinit var team: String

    fun setLatlng(playerId: String, latitude: Double, longitude: Double) =
        multiRepository.setLatlng(room.room_no!!, playerId, latitude, longitude)

    fun getRoomInfo() = multiRepository.getRoomInfo(room.room_no!!)

    fun getMulti() = multiRepository.getMulti(room.room_no!!)

    fun insertMulti(lat: Double, lng: Double) =
        multiRepository.insertMulti(room.room_no!!, lat, lng)

    fun keepItemMulti(multiId: String, playerId: String?, latitude: Double, longitude: Double) =
        multiRepository.insertMultiCollection(
            multiId,
            room.room_no!!,
            playerId,
            team,
            latitude,
            longitude
        )

    fun getScore() = multiRepository.getMultiScore(room.room_no!!)

    private fun distanceOver(latLng1: LatLng, latLng2: LatLng, distance: Float, over: () -> Unit) {
        val d = distanceBetween(
            latLng1.latitude, latLng1.longitude,
            latLng2.latitude, latLng2.longitude
        )

        if (d > distance) over.invoke()
    }

    fun rndMultiItem(rnd: () -> Unit) {
        when {
            room.status == HEAD && switchItem == GameSwitch.ON && roomInfoItems.size != 0 -> {
                switchItem = GameSwitch.OFF
                for (i in 0 until NUMBER_OF_ITEM) rnd.invoke()
            }
            room.status == TAIL && switchItem == GameSwitch.ON && multiItems.size != 0 -> {
                switchItem = GameSwitch.OFF

                multiItems.forEach {
                    distanceOver(
                        sLatLng,
                        LatLng(it.latitude, it.longitude),
                        RADIUS_THREE_KILOMETER
                    ) {
                        rnd.invoke()
                    }
                }
            }
        }
    }

    private fun mission() {
        if (sContext.readPrefFile(KEY_MISSION_MULTI_GAME) == KEY_MISSION_UNSUCCESSFUL) {
            sContext.writePrefFile(
                KEY_MISSION_MULTI_GAME,
                KEY_MISSION_SUCCESSFUL
            )
        }
    }

    fun checkRadius(keepItem: (String, ArrayList<Multi>, ArrayList<Marker>) -> Unit) {
        multiItems.forEachIndexed { index, multi ->
            val distance = distanceBetween(
                sLatLng.latitude, sLatLng.longitude,
                multi.latitude, multi.longitude
            )

            if (distance < RADIUS_ONE_HUNDRED_METER) {
                multiItems.removeAt(index)
                keepItem.invoke(multi.multi_id, multiItems, markerItems)
                sContext.playSoundKeep() // sound
                return
            }
        }
    }

    fun checkEndGame(
        end: (Int, Int) -> Unit,
        play: (Int, Int, Int) -> Unit
    ) {
        when {
            scoreTeamA + scoreTeamB >= 5 -> {
                mission()
                end.invoke(scoreTeamA, scoreTeamB)
            }
            time <= 0 -> {
                mission()
                end.invoke(scoreTeamA, scoreTeamB)
            }
            else -> play.invoke(scoreTeamA, scoreTeamB, time)
        }
    }

    companion object {
        var circlePlayer: Circle? = null
    }

}
