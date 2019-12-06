package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
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

    fun insertRoomInfo(roomNo: String, playerId: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertRoomInfo(roomNo, playerId)
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

    fun insertRoom(name: String, people: String, playerId: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertRoom(name, people, playerId)
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

    fun getRoomInfo(roomNo: String): LiveData<List<RoomInfo>> {
        val liveData = MutableLiveData<List<RoomInfo>>()
        api.getRoomInfo(roomNo)
            .enqueue(object : Callback<List<RoomInfo>> {
                override fun onFailure(call: Call<List<RoomInfo>>, t: Throwable) {}

                override fun onResponse(
                    call: Call<List<RoomInfo>>,
                    response: Response<List<RoomInfo>>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun setTeam(roomNo: String, playerId: String, team: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.setTeam(roomNo, playerId, team)
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

    fun setReady(roomNo: String, playerId: String, status: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.setReady(roomNo, playerId, status)
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