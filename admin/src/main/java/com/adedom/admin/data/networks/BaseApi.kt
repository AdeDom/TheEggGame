package com.adedom.admin.data.networks

import com.adedom.admin.data.models.ItemCollection
import com.adedom.admin.data.models.Logs
import com.adedom.admin.data.models.Player
import com.adedom.library.data.*
import com.adedom.utility.data.BASE_URL
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("admin-get-players.php")
    fun getPlayers(
        @Field(VALUES1) name: String,
        @Field(VALUES2) levelStart: String,
        @Field(VALUES3) levelEnd: String,
        @Field(VALUES4) online: Boolean,
        @Field(VALUES5) offline: Boolean,
        @Field(VALUES6) male: Boolean,
        @Field(VALUES7) female: Boolean
    ): Call<List<Player>>

    @POST("admin-get-item-collection.php")
    fun getItemCollection(): Call<List<ItemCollection>>

    @FormUrlEncoded
    @POST("admin-get-logs.php")
    fun getLogs(
        @Field(VALUES1) dateBegin: String,
        @Field(VALUES2) timeBegin: String,
        @Field(VALUES3) dateEnd: String,
        @Field(VALUES4) timeEnd: String
    ): Call<List<Logs>>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance(BASE_URL)
                .create(BaseApi::class.java)
        }
    }
}