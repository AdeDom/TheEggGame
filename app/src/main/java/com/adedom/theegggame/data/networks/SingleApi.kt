package com.adedom.theegggame.data.networks

import com.adedom.library.data.JsonResponse
import com.adedom.library.data.RetrofitClient
import com.adedom.library.util.*
import com.adedom.theegggame.data.BASE_URL
import com.adedom.theegggame.data.models.Backpack
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SingleApi {

    @FormUrlEncoded
    @POST("insert-item-collection.php")
    fun insertItemCollection(
        @Field(KEY_VALUES1) playerId: String?,
        @Field(KEY_VALUES2) itemId: Int,
        @Field(KEY_VALUES3) qty: Int,
        @Field(KEY_VALUES4) latitude: Double,
        @Field(KEY_VALUES5) longitude: Double
    ): Call<JsonResponse>

    @FormUrlEncoded
    @POST("get-backpack.php")
    fun fetchBackpack(
        @Field(KEY_VALUES1) playerId: String?
    ): Call<Backpack>

    companion object {
        operator fun invoke(): SingleApi {
            return RetrofitClient.instance(BASE_URL)
                .create(SingleApi::class.java)
        }
    }
}