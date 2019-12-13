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
        @Field(VALUES1) randomKey: String,
        @Field(VALUES2) dateIn: String,
        @Field(VALUES3) timeIn: String,
        @Field(VALUES4) playerId: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}