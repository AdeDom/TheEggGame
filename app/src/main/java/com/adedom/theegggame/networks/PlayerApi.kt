package com.adedom.theegggame.networks

import com.adedom.theegggame.models.Player
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlayerApi {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun getPlayerId(
        @Field("values1") username: String,
        @Field("values2") password: String
    ): Response<Player>

    companion object {
        operator fun invoke(): PlayerApi {
            return RetrofitClient.instance()
                .create(PlayerApi::class.java)
        }
    }
}