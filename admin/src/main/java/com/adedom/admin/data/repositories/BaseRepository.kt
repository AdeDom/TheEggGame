package com.adedom.admin.data.repositories

import com.adedom.admin.data.networks.BaseApi
import com.adedom.utility.data.ApiRequest

class BaseRepository(private val api: BaseApi) : ApiRequest() {

    fun getPlayers() = apiRequest { api.getPlayers() }

}
