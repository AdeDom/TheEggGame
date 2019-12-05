package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.networks.SingleApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleRepository(private val api: SingleApi) {

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

}