package com.adedom.theegggame.data.networks

import com.adedom.library.data.JsonResponse
import com.adedom.library.data.RetrofitClient
import com.adedom.library.util.*
import com.adedom.theegggame.data.BASE_URL
import com.adedom.theegggame.data.models.Player
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlayerApi {

    @FormUrlEncoded
    @POST("get-player-id.php")
    fun getPlayerId(
        @Field(KEY_VALUES1) username: String,
        @Field(KEY_VALUES2) password: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-player.php")
    fun getPlayer(
        @Field(KEY_VALUES1) playerId: String
    ): Call<Player>

    @FormUrlEncoded
    @POST("insert-player.php")
    fun insertPlayer(
        @Field(KEY_VALUES1) username: String,
        @Field(KEY_VALUES2) password: String,
        @Field(KEY_VALUES3) name: String,
        @Field(KEY_VALUES4) image: String,
        @Field(KEY_VALUES5) gender: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-players.php")
    fun getPlayers(
        @Field(KEY_VALUES1) search: String,
        @Field(KEY_VALUES2) limit: String
    ): Call<List<Player>>

    @FormUrlEncoded
    @POST("update-profile.php")
    fun updateProfile(
        @Field(KEY_VALUES1) playerId: String?,
        @Field(KEY_VALUES2) name: String,
        @Field(KEY_VALUES3) image: String,
        @Field(KEY_VALUES4) gender: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("update-password.php")
    fun updatePassword(
        @Field(KEY_VALUES1) playerId: String,
        @Field(KEY_VALUES2) oldPassword: String,
        @Field(KEY_VALUES3) newPassword: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): PlayerApi {
            return RetrofitClient.instance(BASE_URL)
                .create(PlayerApi::class.java)
        }
    }

}