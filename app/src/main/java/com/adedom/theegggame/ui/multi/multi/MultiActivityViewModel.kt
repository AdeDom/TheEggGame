package com.adedom.theegggame.ui.multi.multi

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.MultiRepository

class MultiActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun setLatlng(roomNo: String, playerId: String, latitude: Double, longitude: Double) =
        repository.setLatlng(roomNo, playerId, latitude, longitude)

    fun getRoomInfo(roomNo: String) = repository.getRoomInfo(roomNo)

    fun getMulti(roomNo: String) = repository.getMulti(roomNo)

    fun insertMulti(roomNo: String, latitude: Double, longitude: Double) =
        repository.insertMulti(roomNo, latitude, longitude)

    fun insertMultiCollection(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = repository.insertMultiCollection(
        multiId,
        roomNo,
        playerId,
        team,
        latitude,
        longitude,
        date,
        time
    )

    fun getMultiScore(roomNo: String) = repository.getMultiScore(roomNo)

}
