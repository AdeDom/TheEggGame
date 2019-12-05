package com.adedom.theegggame.ui.multi.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.repositories.RoomRepository

class RoomActivityViewModel(private val repository: RoomRepository) : ViewModel() {

    fun getRooms(): LiveData<List<Room>> = repository.getRooms()

    fun getPeopleRoom(roomNo: String): LiveData<JsonResponse> = repository.getPeopleRoom(roomNo)

    fun insertRoomInfo(roomNo: String, playerId: String): LiveData<JsonResponse> {
        return repository.insertRoomInfo(roomNo, playerId)
    }

}
