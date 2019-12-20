package com.adedom.theegggame.ui.multi.room

import com.adedom.theegggame.util.BaseViewModel

class RoomActivityViewModel : BaseViewModel() {

    fun getRooms() = multiRepository.getRooms()

    fun insertRoomInfo(roomNo: String, playerId: String, date: String, time: String) =
        multiRepository.insertRoomInfo(roomNo, playerId, date, time)

    fun insertRoom(name: String, people: String, playerId: String, date: String, time: String) =
        multiRepository.insertRoom(name, people, playerId, date, time)
}
