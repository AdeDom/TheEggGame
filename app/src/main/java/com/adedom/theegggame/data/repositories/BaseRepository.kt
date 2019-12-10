package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.BaseApi
import com.adedom.theegggame.util.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun insertLogs(randomKey: String, dateIn: String, timeIn: String, playerId: String) =
        apiRequest { api.insertLogs(randomKey, dateIn, timeIn, playerId) }

}