package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.Room
import retrofit2.Call
import retrofit2.http.GET

interface RoomApi {

    @GET("get-room.php")
    fun getRooms(): Call<List<Room>>

    companion object {
        operator fun invoke(): RoomApi {
            return RetrofitClient.instance()
                .create(RoomApi::class.java)
        }
    }

}