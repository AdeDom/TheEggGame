package com.adedom.theegggame.data.networks

import com.adedom.library.data.*
import com.adedom.utility.data.BASE_URL
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("insert-logs.php")
    fun insertLogs(
        @Field(KEY_VALUES1) key: String,
        @Field(KEY_VALUES2) dateIn: String,
        @Field(KEY_VALUES3) timeIn: String,
        @Field(KEY_VALUES4) playerId: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("update-logs.php")
    fun updateLogs(
        @Field(KEY_VALUES1) key: String,
        @Field(KEY_VALUES2) dateOut: String,
        @Field(KEY_VALUES3) timeOut: String
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("set-state.php")
    fun setState(
        @Field(KEY_VALUES1) playerId: String,
        @Field(KEY_VALUES2) state: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance(BASE_URL)
                .create(BaseApi::class.java)
        }
    }
}