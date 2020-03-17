package com.adedom.theegggame.data.network

import com.adedom.library.data.JsonResponse
import com.adedom.library.data.RetrofitClient
import com.adedom.library.util.*
import com.adedom.theegggame.data.BASE_URL
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.models.Score
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MultiApi {

    @GET("get-rooms.php")
    fun getRooms(): Call<List<Room>>

    @FormUrlEncoded
    @POST("insert-room-info.php")
    fun insertRoomInfo(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) playerId: String?
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-room.php")
    fun insertRoom(
        @Field(KEY_VALUES1) name: String,
        @Field(KEY_VALUES2) people: String,
        @Field(KEY_VALUES3) playerId: String?
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("delete-player-room-info.php")
    fun deletePlayerRoomInfo(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-room-info.php")
    fun getRoomInfo(
        @Field(KEY_VALUES1) roomNo: String
    ): Call<List<RoomInfo>>

    @FormUrlEncoded
    @POST("set-team.php")
    fun setTeam(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) playerId: String,
        @Field(KEY_VALUES3) team: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-ready.php")
    fun setReady(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) playerId: String,
        @Field(KEY_VALUES3) status: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-room-off.php")
    fun setRoomOff(
        @Field(KEY_VALUES1) roomNo: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-latlng.php")
    fun setLatlng(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) playerId: String,
        @Field(KEY_VALUES3) latitude: Double,
        @Field(KEY_VALUES4) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-multi.php")
    fun insertMulti(
        @Field(KEY_VALUES1) roomNo: String,
        @Field(KEY_VALUES2) latitude: Double,
        @Field(KEY_VALUES3) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-multi.php")
    fun getMulti(
        @Field(KEY_VALUES1) roomNo: String
    ): Call<List<Multi>>

    @FormUrlEncoded
    @POST("insert-multi-collection.php")
    fun insertMultiCollection(
        @Field(KEY_VALUES1) multiId: String,
        @Field(KEY_VALUES2) roomNo: String,
        @Field(KEY_VALUES3) playerId: String?,
        @Field(KEY_VALUES4) team: String,
        @Field(KEY_VALUES5) latitude: Double,
        @Field(KEY_VALUES6) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-multi-score.php")
    fun getMultiScore(
        @Field(KEY_VALUES1) roomNo: String
    ): Call<Score>

    companion object {
        operator fun invoke(): MultiApi {
            return RetrofitClient.instance(BASE_URL)
                .create(MultiApi::class.java)
        }
    }

}