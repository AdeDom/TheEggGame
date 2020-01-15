package com.adedom.theegggame.data.repositories

import com.adedom.library.data.ApiRequest
import com.adedom.theegggame.data.networks.MultiApi

class MultiRepository(private val api: MultiApi) : ApiRequest() {

    fun getRooms() = apiRequest { api.getRooms() }

    fun getRoomInfo(roomNo: String) = apiRequest { api.getRoomInfo(roomNo) }

    fun getMulti(roomNo: String) = apiRequest { api.getMulti(roomNo) }

    fun getMultiScore(roomNo: String) = apiRequest { api.getMultiScore(roomNo) }

    fun insertRoomInfo(roomNo: String, playerId: String, date: String, time: String) =
        apiRequest { api.insertRoomInfo(roomNo, playerId, date, time) }

    fun insertRoom(name: String, people: String, playerId: String, date: String, time: String) =
        apiRequest { api.insertRoom(name, people, playerId, date, time) }

    fun insertMulti(roomNo: String, latitude: Double, longitude: Double) =
        apiRequest { api.insertMulti(roomNo, latitude, longitude) }

    fun insertMultiCollection(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = apiRequest {
        api.insertMultiCollection(
            multiId,
            roomNo,
            playerId,
            team,
            latitude,
            longitude,
            date,
            time
        )
    }

    fun setTeam(roomNo: String, playerId: String, team: String) =
        apiRequest { api.setTeam(roomNo, playerId, team) }

    fun setReady(roomNo: String, playerId: String, status: String) =
        apiRequest { api.setReady(roomNo, playerId, status) }

    fun setRoomOff(roomNo: String) = apiRequest { api.setRoomOff(roomNo) }

    fun setLatlng(roomNo: String, playerId: String, latitude: Double, longitude: Double) =
        apiRequest { api.setLatlng(roomNo, playerId, latitude, longitude) }

    fun deletePlayerRoomInfo(roomNo: String, playerId: String) =
        apiRequest { api.deletePlayerRoomInfo(roomNo, playerId) }

}