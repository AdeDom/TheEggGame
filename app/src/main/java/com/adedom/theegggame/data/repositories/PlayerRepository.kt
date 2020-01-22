package com.adedom.theegggame.data.repositories

import com.adedom.library.data.ApiRequest
import com.adedom.theegggame.data.networks.PlayerApi

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
        gender: String
    ) = apiRequest { api.insertPlayer(username, password, name, image, gender) }

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        apiRequest { api.updatePassword(playerId, oldPassword, newPassword) }

}