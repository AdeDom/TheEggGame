package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Room
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RoomApi {

    @GET("get-room.php")
    fun getRooms(): Call<List<Room>>

    @FormUrlEncoded
    @POST("get-people-room.php")
    fun getPeopleRoom(
        @Field("values1") roomNo: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-room-info.php")
    fun insertRoomInfo(
        @Field("values1") roomNo: String,
        @Field("values2") playerId: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): RoomApi {
            return RetrofitClient.instance()
                .create(RoomApi::class.java)
        }
    }

}