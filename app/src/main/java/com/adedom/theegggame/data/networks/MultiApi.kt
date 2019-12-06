package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MultiApi {

    @GET("get-room.php")
    fun getRooms(): Call<List<Room>>

    @FormUrlEncoded
    @POST("insert-room-info.php")
    fun insertRoomInfo(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-room.php")
    fun insertRoom(
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

    @FormUrlEncoded
    @POST("get-room-info.php")
    fun getRoomInfo(
        @Field("values1") roomNo: String
    ): Call<List<RoomInfo>>

    @FormUrlEncoded
    @POST("set-team.php")
    fun setTeam(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String,
        @Field("values3") team: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-ready.php")
    fun setReady(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String,
        @Field("values3") status: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): MultiApi {
            return RetrofitClient.instance()
                .create(MultiApi::class.java)
        }
    }

}