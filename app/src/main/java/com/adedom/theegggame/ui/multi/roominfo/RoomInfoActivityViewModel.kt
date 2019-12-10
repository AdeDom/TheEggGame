package com.adedom.theegggame.ui.multi.roominfo

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.MultiRepository

class RoomInfoActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun deletePlayer(roomNo: String, playerId: String) = repository.deletePlayer(roomNo, playerId)

    fun getRoomInfo(roomNo: String) = repository.getRoomInfo(roomNo)

    fun setTeam(roomNo: String, playerId: String, team: String) =
        repository.setTeam(roomNo, playerId, team)

    fun setReady(roomNo: String, playerId: String, status: String) =
        repository.setReady(roomNo, playerId, status)

    fun setRoomOff(roomNo: String) = repository.setRoomOff(roomNo)

}
