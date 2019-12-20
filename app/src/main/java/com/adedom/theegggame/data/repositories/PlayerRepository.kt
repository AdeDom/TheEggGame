package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.utility.data.ApiRequest

class PlayerRepository(private val api: PlayerApi) : ApiRequest() {

    fun getPlayerId(username: String, password: String) =
        apiRequest { api.getPlayerId(username, password) }

    fun getPlayer(playerId: String) = apiRequest { api.getPlayer(playerId) }

    fun getPlayers(search: String, limit: String) = apiRequest { api.getPlayers(search, limit) }

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String,
        date: String,
        time: String
    ) = apiRequest { api.insertPlayer(username, password, name, image, date, time) }

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        apiRequest { api.updatePassword(playerId, oldPassword, newPassword) }

}