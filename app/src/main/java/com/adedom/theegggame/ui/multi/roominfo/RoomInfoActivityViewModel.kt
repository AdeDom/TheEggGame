package com.adedom.theegggame.ui.multi.roominfo

import com.adedom.theegggame.util.BaseViewModel

class RoomInfoActivityViewModel : BaseViewModel() {

    fun deletePlayer(roomNo: String, playerId: String) =
        multiRepository.deletePlayer(roomNo, playerId)

    fun getRoomInfo(roomNo: String) = multiRepository.getRoomInfo(roomNo)

    fun setTeam(roomNo: String, playerId: String, team: String) =
        multiRepository.setTeam(roomNo, playerId, team)

    fun setReady(roomNo: String, playerId: String, status: String) =
        multiRepository.setReady(roomNo, playerId, status)

    fun setRoomOff(roomNo: String) = multiRepository.setRoomOff(roomNo)
}
