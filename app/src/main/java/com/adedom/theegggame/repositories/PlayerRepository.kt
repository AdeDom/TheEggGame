package com.adedom.theegggame.repositories

import com.adedom.theegggame.networks.PlayerApi

class PlayerRepository(
    private val api: PlayerApi
) : SafeApiRequest() {

    suspend fun getPlayerId(username: String, password: String) =
        apiRequest { api.getPlayerId(username, password) }
}