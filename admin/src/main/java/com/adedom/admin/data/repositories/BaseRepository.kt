package com.adedom.admin.data.repositories

import com.adedom.admin.data.networks.BaseApi
import com.adedom.library.data.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun getPlayers(
        name: String,
        levelStart: String,
        levelEnd: String,
        online: Boolean,
        offline: Boolean,
        male: Boolean,
        female: Boolean
    ) = apiRequest { api.getPlayers(name, levelStart, levelEnd, online, offline, male, female) }

    fun getItemCollection(name: String, itemId: Int) =
        apiRequest { api.getItemCollection(name, itemId) }

    fun getLogs(dateBegin: String, timeBegin: String, dateEnd: String, timeEnd: String) =
        apiRequest { api.getLogs(dateBegin, timeBegin, dateEnd, timeEnd) }

}
