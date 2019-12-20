package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.BaseApi
import com.adedom.utility.data.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun insertLogs(randomKey: String, dateIn: String, timeIn: String, playerId: String) =
        apiRequest { api.insertLogs(randomKey, dateIn, timeIn, playerId) }

    fun updateLogs(randomKey: String, dateOut: String, timeOut: String) =
        apiRequest { api.updateLogs(randomKey, dateOut, timeOut) }

    fun setState(playerId: String, state: String) = apiRequest { api.setState(playerId, state) }

}