package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.RoomApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRepository(private val api: RoomApi) {

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

}