package com.adedom.theegggame.ui.multi.multi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.models.Score
import com.adedom.theegggame.data.repositories.MultiRepository

class MultiActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun setLatlng(
        roomNo: String,
        playerId: String,
        latitude: Double,
        longitude: Double
    ): LiveData<JsonResponse> {
        return repository.setLatlng(roomNo, playerId, latitude, longitude)
    }

    fun getRoomInfo(roomNo: String): LiveData<List<RoomInfo>> = repository.getRoomInfo(roomNo)

    fun getMulti(roomNo: String): LiveData<List<Multi>> = repository.getMulti(roomNo)

    fun insertMulti(
        roomNo: String,
        latitude: Double,
        longitude: Double
    ): LiveData<JsonResponse> {
        return repository.insertMulti(roomNo, latitude, longitude)
    }

    fun insertMultiCollection(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String
    ): LiveData<JsonResponse> {
        return repository.insertMultiCollection(multiId, roomNo, playerId, team)
    }

    fun getMultiScore(roomNo: String): LiveData<Score> {
        return repository.getMultiScore(roomNo)
    }

}
