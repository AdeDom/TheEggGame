package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.networks.BaseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseRepository(private val api: BaseApi) {

    fun insertLogs(
        randomKey: String,
        dateIn: String,
        timeIn: String,
        playerId: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertLogs(randomKey, dateIn, timeIn, playerId)
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