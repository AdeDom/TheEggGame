package com.adedom.theegggame.data.repositories

import androidx.lifecycle.MutableLiveData
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository {

    private val api = RetrofitClient.instance().create(PlayerApi::class.java)

    val player = MutableLiveData<Player>()

    val players = MutableLiveData<List<Player>>()

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

    fun getPlayerRank(
        search: String,
        limit: String
    ) {
        api.getPlayerRank(search, limit)
            .enqueue(object : Callback<List<Player>> {
                override fun onFailure(call: Call<List<Player>>, t: Throwable) {}

                override fun onResponse(
                    call: Call<List<Player>>,
                    response: Response<List<Player>>
                ) {
                    if (!response.isSuccessful) return

                    players.value = response.body()
                }
            })
    }
}