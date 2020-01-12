package com.adedom.theegggame.data.networks

import com.adedom.utility.data.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("insert-logs.php")
    fun insertLogs(
        @Field(VALUES1) key: String,
        @Field(VALUES2) dateIn: String,
        @Field(VALUES3) timeIn: String,
        @Field(VALUES4) playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("update-logs.php")
    fun updateLogs(
        @Field(VALUES1) key: String,
        @Field(VALUES2) dateOut: String,
        @Field(VALUES3) timeOut: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-state.php")
    fun setState(
        @Field(VALUES1) playerId: String,
        @Field(VALUES2) state: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}