package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.utility.data.ApiRequest

class PlayerRepository(private val api: PlayerApi) : ApiRequest() {

    fun getPlayerIdLogin(username: String, password: String) =
        apiRequest { api.getPlayerIdLogin(username, password) }

    fun getPlayers(playerId: String) = apiRequest { api.getPlayers(playerId) }

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String,
        date: String,
        time: String
    ) = apiRequest { api.insertPlayer(username, password, name, image, date, time) }

    fun getPlayerRank(search: String, limit: String) =
        apiRequest { api.getPlayerRank(search, limit) }

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        apiRequest { api.updatePassword(playerId, oldPassword, newPassword) }

}