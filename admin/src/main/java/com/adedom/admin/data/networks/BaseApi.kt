package com.adedom.admin.data.networks

import com.adedom.admin.data.BASE_URL
import com.adedom.admin.data.models.ItemCollection
import com.adedom.admin.data.models.Logs
import com.adedom.admin.data.models.Player
import com.adedom.library.data.RetrofitClient
import com.adedom.library.util.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("admin-get-players.php")
    fun getPlayers(
        @Field(KEY_VALUES1) name: String,
        @Field(KEY_VALUES2) levelStart: String,
        @Field(KEY_VALUES3) levelEnd: String,
        @Field(KEY_VALUES4) online: Boolean,
        @Field(KEY_VALUES5) offline: Boolean,
        @Field(KEY_VALUES6) male: Boolean,
        @Field(KEY_VALUES7) female: Boolean
    ): Call<List<Player>>

    @FormUrlEncoded
    @POST("admin-get-item-collection.php")
    fun getItemCollection(
        @Field(KEY_VALUES1) name: String,
        @Field(KEY_VALUES2) itemId: Int
    ): Call<List<ItemCollection>>

    @FormUrlEncoded
    @POST("admin-get-logs.php")
    fun getLogs(
        @Field(KEY_VALUES1) dateBegin: String,
        @Field(KEY_VALUES2) timeBegin: String,
        @Field(KEY_VALUES3) dateEnd: String,
        @Field(KEY_VALUES4) timeEnd: String
    ): Call<List<Logs>>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance(BASE_URL)
                .create(BaseApi::class.java)
        }
    }
}