package com.adedom.theegggame.data.network

//import com.adedom.library.data.JsonResponse
//import com.adedom.library.data.RetrofitClient
//import com.adedom.library.util.KEY_VALUES1
//import com.adedom.library.util.KEY_VALUES2
//import com.adedom.theegggame.data.BASE_URL
//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.POST
//
//interface BaseApi {
//
//    @FormUrlEncoded
//    @POST("insert-logs.php")
//    fun insertLogs(
//        @Field(KEY_VALUES1) key: String,
//        @Field(KEY_VALUES2) playerId: String
//    ): Call<JsonResponse>
//
//    @FormUrlEncoded
//    @POST("update-logs.php")
//    fun updateLogs(
//        @Field(KEY_VALUES1) key: String
//    ): Call<JsonResponse>
//
//    @FormUrlEncoded
//    @POST("set-state.php")
//    fun setState(
//        @Field(KEY_VALUES1) playerId: String,
//        @Field(KEY_VALUES2) state: String
//    ): Call<JsonResponse>
//
//    companion object {
//        operator fun invoke(): BaseApi {
//            return RetrofitClient.instance(BASE_URL)
//                .create(BaseApi::class.java)
//        }
//    }
//}