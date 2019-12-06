package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Player
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlayerApi {

    @FormUrlEncoded
    @POST("get-player-id-login.php")
    fun getPlayerIdLogin(
        @Field("values1") username: String,
        @Field("values2") password: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("get-player.php")
    fun getPlayers(
        @Field("values1") playerId: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("insert-player.php")
    fun insertPlayer(
        @Field("values1") username: String,
        @Field("values2") password: String,
        @Field("values3") name: String,
        @Field("values4") image: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("get-player-rank.php")
    fun getPlayerRank(
        @Field("values1") search: String,
        @Field("values2") limit: String
    ): Call<List<Player>>

    @FormUrlEncoded
    @POST("update-password.php")
    fun updatePassword(
        @Field("values1") playerId: String,
        @Field("values2") oldPassword: String,
        @Field("values3") newPassword: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): PlayerApi {
            return RetrofitClient.instance()
                .create(PlayerApi::class.java)
        }
    }

}