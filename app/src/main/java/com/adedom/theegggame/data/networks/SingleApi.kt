package com.adedom.theegggame.data.networks

import com.adedom.utility.data.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SingleApi {

    @FormUrlEncoded
    @POST("insert-item-collection.php")
    fun insertItemCollection(
        @Field(VALUES1) playerId: String,
        @Field(VALUES2) itemId: Int,
        @Field(VALUES3) qty: Int,
        @Field(VALUES4) latitude: Double,
        @Field(VALUES5) longitude: Double,
        @Field(VALUES6) date: String,
        @Field(VALUES7) time: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): SingleApi {
            return RetrofitClient.instance()
                .create(SingleApi::class.java)
        }
    }
}