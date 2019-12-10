package com.adedom.theegggame.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiRequest {

    val TAG = "ApiRequest"

    fun <T : Any> apiRequest(call: () -> Call<T>): LiveData<T> {
        val liveData = MutableLiveData<T>()
        call.invoke().enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d(TAG, ">>${t.message}")
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                Log.d(TAG, ">>${response.code()}")
                if (!response.isSuccessful) return
                liveData.value = response.body()
            }
        })
        return liveData
    }
}