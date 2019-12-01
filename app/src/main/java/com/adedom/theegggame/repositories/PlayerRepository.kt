package com.adedom.theegggame.repositories

import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.networks.PlayerApi
import com.adedom.theegggame.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository {


    private val api = RetrofitClient.instance().create(PlayerApi::class.java)

    val player = MutableLiveData<Player>()

    fun getPlayerId(
        username: String,
        password: String
    ) {
        api.getPlayerId(username, password)
            .enqueue(object : Callback<Player> {
                override fun onFailure(call: Call<Player>, t: Throwable) {}

                override fun onResponse(call: Call<Player>, response: Response<Player>) {
                    if (!response.isSuccessful) return

                    player.value = response.body()
                }
            })
    }

    fun getPlayers(
        playerId: String
    ) {
        api.getPlayers(playerId)
            .enqueue(object : Callback<Player> {
                override fun onFailure(call: Call<Player>, t: Throwable) {}

                override fun onResponse(call: Call<Player>, response: Response<Player>) {
                    if (!response.isSuccessful) return

                    player.value = response.body()
                }
            })
    }

    fun registerPlayer(
        username: String,
        password: String,
        name: String,
        image: String
    ) {
        api.registerPlayers(username, password, name, image)
            .enqueue(object : Callback<Player> {
                override fun onFailure(call: Call<Player>, t: Throwable) {}

                override fun onResponse(call: Call<Player>, response: Response<Player>) {
                    if (!response.isSuccessful) return

                    player.value = response.body()
                }
            })
    }
}