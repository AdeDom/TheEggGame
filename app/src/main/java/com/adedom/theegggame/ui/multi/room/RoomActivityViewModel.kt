package com.adedom.theegggame.ui.multi.room

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.MultiRepository

class RoomActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun getRooms() = repository.getRooms()

    fun insertRoomInfo(roomNo: String, playerId: String, date: String, time: String) =
        repository.insertRoomInfo(roomNo, playerId, date, time)

}
