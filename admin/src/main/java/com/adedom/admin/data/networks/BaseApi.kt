package com.adedom.admin.data.networks

import com.adedom.admin.data.models.Player
import com.adedom.utility.data.RetrofitClient
import com.adedom.utility.data.VALUES1
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("admin-get-players.php")
    fun getPlayers(
        @Field(VALUES1) search: String
    ): Call<List<Player>>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}