package com.adedom.admin.data.repositories

import com.adedom.admin.data.networks.BaseApi

class BaseRepository(private val api: BaseApi):ApiRequest() {

//    fun getPlayers(): LiveData<List<Player>> {
//        val liveData = MutableLiveData<List<Player>>()
//        api.getPlayers().enqueue(object : Callback<List<Player>> {
//            override fun onFailure(call: Call<List<Player>>, t: Throwable) {}
//
//            override fun onResponse(call: Call<List<Player>>, response: Response<List<Player>>) {
//                if (!response.isSuccessful) return
//                liveData.value = response.body()
//            }
//        })
//        return liveData
//    }

    fun getPlayers() = apiRequest { api.getPlayers() }

}
