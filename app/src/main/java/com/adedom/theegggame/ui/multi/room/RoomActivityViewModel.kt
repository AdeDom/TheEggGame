package com.adedom.theegggame.ui.multi.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.repositories.MultiRepository

class RoomActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun getRooms(): LiveData<List<Room>> = repository.getRooms()

    fun insertRoomInfo(
        roomNo: String,
        playerId: String,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        return repository.insertRoomInfo(roomNo, playerId, date, time)
    }
}
