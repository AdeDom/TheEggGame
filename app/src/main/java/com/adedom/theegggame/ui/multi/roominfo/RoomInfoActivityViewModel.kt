package com.adedom.theegggame.ui.multi.roominfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.repositories.MultiRepository

class RoomInfoActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun deletePlayer(roomNo: String, playerId: String): LiveData<JsonResponse> {
        return repository.deletePlayer(roomNo, playerId)
    }

    fun getRoomInfo(roomNo: String): LiveData<List<RoomInfo>> = repository.getRoomInfo(roomNo)

    fun setTeam(roomNo: String, playerId: String, team: String): LiveData<JsonResponse> {
        return repository.setTeam(roomNo, playerId, team)
    }

    fun setReady(roomNo: String, playerId: String, status: String): LiveData<JsonResponse> {
        return repository.setReady(roomNo, playerId, status)
    }

    fun setRoomOff(roomNo: String): LiveData<JsonResponse> = repository.setRoomOff(roomNo)

}
