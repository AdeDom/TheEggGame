package com.adedom.theegggame.ui.multi.room

import com.adedom.theegggame.util.BaseViewModel

class RoomActivityViewModel : BaseViewModel() {

    fun getRooms() = multiRepository.getRooms()

    fun joinRoom(roomNo: String, playerId: String, date: String, time: String) =
        multiRepository.insertRoomInfo(roomNo, playerId, date, time)

    fun createRoom(name: String, people: String, playerId: String, date: String, time: String) =
        multiRepository.insertRoom(name, people, playerId, date, time)
}


