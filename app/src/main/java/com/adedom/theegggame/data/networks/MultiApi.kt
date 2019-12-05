package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MultiApi {

    @GET("get-room.php")
    fun getRooms(): Call<List<Room>>

    @FormUrlEncoded
    @POST("join-room.php")
    fun joinRoom(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("create-room.php")
    fun createRoom(
        @Field("values1") name: String,
        @Field("values2") people: String,
        @Field("values3") playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("delete-player-room-info.php")
    fun deletePlayer(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): MultiApi {
            return RetrofitClient.instance()
                .create(MultiApi::class.java)
        }
    }

}