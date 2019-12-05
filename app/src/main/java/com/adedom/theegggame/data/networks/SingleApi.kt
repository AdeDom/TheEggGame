package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SingleApi {

//    Call<Void>

    @FormUrlEncoded
    @POST("insert-item.php")
    fun insertItem(
        @Field("values1") playerId: String,
        @Field("values2") itemId: Int,
        @Field("values3") qty: Int
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): SingleApi {
            return RetrofitClient.instance()
                .create(SingleApi::class.java)
        }
    }
}