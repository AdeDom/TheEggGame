package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.MultiApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MultiRepository(private val api: MultiApi) {

    fun getRooms(): LiveData<List<Room>> {
        val liveData = MutableLiveData<List<Room>>()
        api.getRooms()
            .enqueue(object : Callback<List<Room>> {
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {}

                override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun joinRoom(roomNo: String, playerId: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.joinRoom(roomNo, playerId)
            .enqueue(object : Callback<JsonResponse> {
                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun createRoom(name: String, people: String, playerId: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.createRoom(name, people, playerId)
            .enqueue(object : Callback<JsonResponse> {
                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun deletePlayer(roomNo: String, playerId: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.deletePlayer(roomNo, playerId)
            .enqueue(object : Callback<JsonResponse> {
                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }


}