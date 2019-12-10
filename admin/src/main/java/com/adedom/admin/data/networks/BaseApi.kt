package com.adedom.admin.data.networks

import com.adedom.admin.data.models.Player
import retrofit2.Call
import retrofit2.http.POST

interface BaseApi {

    //    @FormUrlEncoded
    @POST("admin-get-players.php")
    fun getPlayers(): Call<List<Player>>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}