package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.BaseApi
import com.adedom.utility.data.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun insertLogs(key: String, dateIn: String, timeIn: String, playerId: String) =
        apiRequest { api.insertLogs(key, dateIn, timeIn, playerId) }

    fun updateLogs(key: String, dateOut: String, timeOut: String) =
        apiRequest { api.updateLogs(key, dateOut, timeOut) }

    fun setState(playerId: String, state: String) = apiRequest { api.setState(playerId, state) }

}