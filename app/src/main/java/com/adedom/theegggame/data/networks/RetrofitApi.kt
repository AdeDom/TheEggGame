package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitApi {

//    Call<Void>

    @FormUrlEncoded
    @POST("change-password.php")
    fun changePassword(
        @Field("values1") playerId: String,
        @Field("values2") oldPassword: String,
        @Field("values3") newPassword: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("insert-item.php")
    fun insertItem(
        @Field("values1") playerId: String,
        @Field("values2") itemId: Int,
        @Field("values3") qty: Int
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-people-room.php")
    fun getPeopleRoom(
        @Field("values1") roomNo: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): RetrofitApi {
            return RetrofitClient.instance()
                .create(RetrofitApi::class.java)
        }
    }
}