package com.adedom.theegggame.data.repositories

import com.adedom.library.data.ApiRequest
import com.adedom.theegggame.data.networks.BaseApi

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun insertLogs(key: String, playerId: String) = apiRequest { api.insertLogs(key, playerId) }

    fun updateLogs(key: String) = apiRequest { api.updateLogs(key) }

    fun setState(playerId: String, state: String) = apiRequest { api.setState(playerId, state) }

}