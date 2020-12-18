package com.adedom.theegggame.ui.multi.multi

import androidx.lifecycle.ViewModel
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.library.util.distanceBetween
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.Marker

class MultiActivityViewModel : ViewModel() {

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

    fun rndMultiItem(rnd: () -> Unit) {
        //TODO items tag

        //TODO insert items five only

        when {
            room.status == HEAD && switchItem == GameSwitch.ON && roomInfoItems.size != 0 -> {
                switchItem = GameSwitch.OFF
                for (i in 0 until NUMBER_OF_ITEM) rnd.invoke()
            }
            room.status == TAIL && switchItem == GameSwitch.ON && multiItems.size != 0 -> {
                switchItem = GameSwitch.OFF

                multiItems.forEach {
                    val d = distanceBetween(
                        sLatLng.latitude,
                        sLatLng.longitude,
                        it.latitude,
                        it.longitude,
                    )
                    if (d > RADIUS_THREE_KILOMETER) {
                        rnd.invoke()
                    }
                }
            }
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
                if (sContext.readPrefFile(KEY_MISSION_MULTI_GAME) == KEY_MISSION_UNSUCCESSFUL) {
                    sContext.writePrefFile(
                        KEY_MISSION_MULTI_GAME,
                        KEY_MISSION_SUCCESSFUL
                    )
                }
                end.invoke(scoreTeamA, scoreTeamB)
            }
            time <= 0 -> {
                if (sContext.readPrefFile(KEY_MISSION_MULTI_GAME) == KEY_MISSION_UNSUCCESSFUL) {
                    sContext.writePrefFile(
                        KEY_MISSION_MULTI_GAME,
                        KEY_MISSION_SUCCESSFUL
                    )
                }
                end.invoke(scoreTeamA, scoreTeamB)
            }
            else -> play.invoke(scoreTeamA, scoreTeamB, time)
        }
    }

}
