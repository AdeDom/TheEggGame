package com.adedom.admin.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiRequest {
    fun <T : Any> apiRequest(call: () -> Call<T>): LiveData<T> {
        val liveData = MutableLiveData<T>()
        call.invoke().enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {}
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (!response.isSuccessful) return
                liveData.value = response.body()
            }
        })
        return liveData
    }
}