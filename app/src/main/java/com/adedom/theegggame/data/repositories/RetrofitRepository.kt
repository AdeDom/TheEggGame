package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.networks.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitRepository(private val api: RetrofitApi) {

    fun changePassword(
        playerId: String,
        oldPassword: String,
        newPassword: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()

        api.changePassword(playerId, oldPassword, newPassword)
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

    fun insertItem(playerId: String, itemId: Int, qty: Int): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()

        api.insertItem(playerId, itemId, qty)
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

    fun getPeopleRoom(roomNo: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()

        api.getPeopleRoom(roomNo)
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