package com.adedom.theegggame.ui.multi.roominfo

import android.content.Context
import android.widget.Toast
import com.adedom.library.extension.toast
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*

class RoomInfoActivityViewModel : BaseViewModel() {

    private var _ready = KEY_UNREADY
    lateinit var room: Room
    var roomInfo = arrayListOf<RoomInfo>()
    var team: String = KEY_STRING

//    fun deletePlayerRoomInfo(playerId: String) =
//        multiRepository.deletePlayerRoomInfo(room.room_no!!, playerId)
//
//    fun getRoomInfo() = multiRepository.getRoomInfo(room.room_no!!)
//
//    fun setTeam(playerId: String) =
//        multiRepository.setTeam(room.room_no!!, playerId, team)
//
//    fun getReady(playerId: String, status: String) =
//        multiRepository.setReady(room.room_no!!, playerId, status)
//
//    fun setRoomOff() = multiRepository.setRoomOff(room.room_no!!)

    fun getReady(): String {
        _ready = if (_ready == KEY_UNREADY) KEY_READY else KEY_UNREADY
        return _ready
    }

    fun checkReadyToStartGame(context: Context, play: () -> Unit) {
        if (room.status == HEAD) {
            val count = roomInfo.count { it.status == KEY_READY }
            val teamA = roomInfo.count { it.team == TEAM_A }
            val teamB = roomInfo.count { it.team == TEAM_B }

            when {
                //player not yet
                count != roomInfo.lastIndex -> {
                    context.toast(R.string.player_not_ready, Toast.LENGTH_LONG)
                    return
                }
                //check team
                teamA == 0 || teamB == 0 -> {
                    context.toast(R.string.least_one_person_per_team, Toast.LENGTH_LONG)
                    return
                }
                count == roomInfo.lastIndex -> play.invoke()
            }
        } else play.invoke()
    }

    fun checkStartGame(play: () -> Unit) {
        val roomInfoSize = roomInfo.size
        val numReady = roomInfo.count { it.status == KEY_READY }
        if (roomInfoSize == numReady && roomInfoSize != 0) play.invoke()
    }


}
