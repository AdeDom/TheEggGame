package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.*
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

    fun insertRoomInfo(
        roomNo: String,
        playerId: String,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertRoomInfo(roomNo, playerId, date, time)
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

    fun insertRoom(
        name: String,
        people: String,
        playerId: String,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertRoom(name, people, playerId, date, time)
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

    fun setRoomOff(roomNo: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.setRoomOff(roomNo)
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

    fun setLatlng(
        roomNo: String,
        playerId: String,
        latitude: Double,
        longitude: Double
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.setLatlng(roomNo, playerId, latitude, longitude)
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

    fun getMulti(roomNo: String): LiveData<List<Multi>> {
        val liveData = MutableLiveData<List<Multi>>()
        api.getMulti(roomNo)
            .enqueue(object : Callback<List<Multi>> {
                override fun onFailure(call: Call<List<Multi>>, t: Throwable) {}
                override fun onResponse(call: Call<List<Multi>>, response: Response<List<Multi>>) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun insertMulti(
        roomNo: String,
        latitude: Double,
        longitude: Double
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertMulti(roomNo, latitude, longitude)
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

    fun insertMultiCollection(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertMultiCollection(multiId, roomNo, playerId, team, latitude, longitude, date, time)
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

    fun getMultiScore(roomNo: String): LiveData<Score> {
        val liveData = MutableLiveData<Score>()
        api.getMultiScore(roomNo)
            .enqueue(object : Callback<Score> {
                override fun onFailure(call: Call<Score>, t: Throwable) {}
                override fun onResponse(
                    call: Call<Score>,
                    response: Response<Score>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

}