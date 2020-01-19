package com.adedom.theegggame.ui.multi.roominfo

import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.BaseViewModel
import com.adedom.theegggame.util.KEY_READY
import com.adedom.theegggame.util.KEY_UNREADY

class RoomInfoActivityViewModel : BaseViewModel() {

    private var _ready = KEY_UNREADY
    var roomInfo = arrayListOf<RoomInfo>()

    fun deletePlayerRoomInfo(roomNo: String, playerId: String) =
        multiRepository.deletePlayerRoomInfo(roomNo, playerId)

    fun getRoomInfo(roomNo: String) = multiRepository.getRoomInfo(roomNo)

    fun setTeam(roomNo: String, playerId: String, team: String) =
        multiRepository.setTeam(roomNo, playerId, team)

    fun getReady(roomNo: String, playerId: String, status: String) =
        multiRepository.setReady(roomNo, playerId, status)

    fun setRoomOff(roomNo: String) = multiRepository.setRoomOff(roomNo)

    fun getReady(): String {
        _ready = if (_ready == KEY_UNREADY) KEY_READY else KEY_UNREADY
        return _ready
    }

    companion object {
        var team: String = ""
        lateinit var sRoom: Room

    }
}
