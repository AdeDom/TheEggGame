package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.utility.VALUES1
import com.adedom.utility.VALUES2
import com.adedom.utility.VALUES3
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SingleApi {

    @FormUrlEncoded
    @POST("insert-item.php")
    fun insertItem(
        @Field(VALUES1) playerId: String,
        @Field(VALUES2) itemId: Int,
        @Field(VALUES3) qty: Int
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): SingleApi {
            return RetrofitClient.instance()
                .create(SingleApi::class.java)
        }
    }
}