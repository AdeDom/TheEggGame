package com.adedom.theegggame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.networks.PlayerApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository(private val api: PlayerApi) {

    fun getPlayerIdLogin(username: String, password: String): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.getPlayerIdLogin(username, password)
            .enqueue(object : Callback<JsonResponse> {
                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {}
                override fun onResponse(call: Call<JsonResponse>, response: Response<JsonResponse>) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun getPlayers(playerId: String): LiveData<Player> {
        val liveData = MutableLiveData<Player>()
        api.getPlayers(playerId)
            .enqueue(object : Callback<Player> {
                override fun onFailure(call: Call<Player>, t: Throwable) {}
                override fun onResponse(call: Call<Player>, response: Response<Player>) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.insertPlayer(username, password, name, image)
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

    fun getPlayerRank(search: String, limit: String): LiveData<List<Player>> {
        val liveData = MutableLiveData<List<Player>>()
        api.getPlayerRank(search, limit)
            .enqueue(object : Callback<List<Player>> {
                override fun onFailure(call: Call<List<Player>>, t: Throwable) {}
                override fun onResponse(
                    call: Call<List<Player>>,
                    response: Response<List<Player>>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }

    fun updatePassword(
        playerId: String,
        oldPassword: String,
        newPassword: String
    ): LiveData<JsonResponse> {
        val liveData = MutableLiveData<JsonResponse>()
        api.updatePassword(playerId, oldPassword, newPassword)
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