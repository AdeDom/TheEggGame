package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Player
import com.adedom.utility.VALUES1
import com.adedom.utility.VALUES2
import com.adedom.utility.VALUES3
import com.adedom.utility.VALUES4
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlayerApi {

    @FormUrlEncoded
    @POST("get-player-id-login.php")
    fun getPlayerIdLogin(
        @Field(VALUES1) username: String,
        @Field(VALUES2) password: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-player.php")
    fun getPlayers(
        @Field(VALUES1) playerId: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("insert-player.php")
    fun insertPlayer(
        @Field(VALUES1) username: String,
        @Field(VALUES2) password: String,
        @Field(VALUES3) name: String,
        @Field(VALUES4) image: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-player-rank.php")
    fun getPlayerRank(
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