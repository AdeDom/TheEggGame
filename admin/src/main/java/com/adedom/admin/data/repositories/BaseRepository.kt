package com.adedom.admin.data.repositories

import com.adedom.admin.data.networks.BaseApi

class BaseRepository(private val api: BaseApi):ApiRequest() {

    fun getPlayers() = apiRequest { api.getPlayers() }

}
