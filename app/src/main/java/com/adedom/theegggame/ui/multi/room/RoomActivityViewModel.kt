package com.adedom.theegggame.ui.multi.room

import com.adedom.theegggame.util.BaseViewModel

class RoomActivityViewModel : BaseViewModel() {

    fun getRooms() = multiRepository.getRooms()

    fun insertRoomInfo(roomNo: String, playerId: String, date: String, time: String) =
        multiRepository.insertRoomInfo(roomNo, playerId, date, time)
}
