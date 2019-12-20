package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.Player
import com.adedom.utility.data.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlayerApi {

    @FormUrlEncoded
    @POST("get-player-id.php")
    fun getPlayerId(
        @Field(VALUES1) username: String,
        @Field(VALUES2) password: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-player.php")
    fun getPlayer(
        @Field(VALUES1) playerId: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("insert-player.php")
    fun insertPlayer(
        @Field(VALUES1) username: String,
        @Field(VALUES2) password: String,
        @Field(VALUES3) name: String,
        @Field(VALUES4) image: String,
        @Field(VALUES5) date: String,
        @Field(VALUES6) time: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-players.php")
    fun getPlayers(
        @Field(VALUES1) search: String,
        @Field(VALUES2) limit: String
    ): Call<List<Player>>

    @FormUrlEncoded
    @POST("update-password.php")
    fun updatePassword(
        @Field(VALUES1) playerId: String,
        @Field(VALUES2) oldPassword: String,
        @Field(VALUES3) newPassword: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): PlayerApi {
            return RetrofitClient.instance()
                .create(PlayerApi::class.java)
        }
    }

}