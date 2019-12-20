package com.adedom.admin.data.repositories

import com.adedom.admin.data.networks.BaseApi
import com.adedom.utility.data.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun getPlayers(name: String, level: String, online: Boolean, offline: Boolean) =
        apiRequest { api.getPlayers(name, level, online, offline) }

    fun getItemCollection() = apiRequest { api.getItemCollection() }

    fun getLogs(dateBegin: String, timeBegin: String, dateEnd: String, timeEnd: String) =
        apiRequest { api.getLogs(dateBegin, timeBegin, dateEnd, timeEnd) }

}
