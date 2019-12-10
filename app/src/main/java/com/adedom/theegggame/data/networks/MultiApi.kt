package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.*
import com.adedom.utility.*
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
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) playerId: String,
        @Field(VALUES3) date: String,
        @Field(VALUES4) time: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-room.php")
    fun insertRoom(
        @Field(VALUES1) name: String,
        @Field(VALUES2) people: String,
        @Field(VALUES3) playerId: String,
        @Field(VALUES4) date: String,
        @Field(VALUES5) time: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("delete-player-room-info.php")
    fun deletePlayer(
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-room-info.php")
    fun getRoomInfo(
        @Field(VALUES1) roomNo: String
    ): Call<List<RoomInfo>>

    @FormUrlEncoded
    @POST("set-team.php")
    fun setTeam(
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) playerId: String,
        @Field(VALUES3) team: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-ready.php")
    fun setReady(
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) playerId: String,
        @Field(VALUES3) status: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-room-off.php")
    fun setRoomOff(
        @Field(VALUES1) roomNo: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-latlng.php")
    fun setLatlng(
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) playerId: String,
        @Field(VALUES3) latitude: Double,
        @Field(VALUES4) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-multi.php")
    fun insertMulti(
        @Field(VALUES1) roomNo: String,
        @Field(VALUES2) latitude: Double,
        @Field(VALUES3) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-multi.php")
    fun getMulti(
        @Field(VALUES1) roomNo: String
    ): Call<List<Multi>>

    @FormUrlEncoded
    @POST("insert-multi-collection.php")
    fun insertMultiCollection(
        @Field(VALUES1) multiId: String,
        @Field(VALUES2) roomNo: String,
        @Field(VALUES3) playerId: String,
        @Field(VALUES4) team: String,
        @Field(VALUES5) latitude: Double,
        @Field(VALUES6) longitude: Double,
        @Field(VALUES7) date: String,
        @Field(VALUES8) time: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-multi-score.php")
    fun getMultiScore(
        @Field(VALUES1) roomNo: String
    ): Call<Score>

    companion object {
        operator fun invoke(): MultiApi {
            return RetrofitClient.instance()
                .create(MultiApi::class.java)
        }
    }

}