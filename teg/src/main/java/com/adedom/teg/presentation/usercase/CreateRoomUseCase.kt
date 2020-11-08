package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.CreateRoomRequest
import com.adedom.teg.models.response.BaseResponse

interface CreateRoomUseCase {

    fun validateMaxRoomPeople(roomPeople: Int): Int

    fun validateMinRoomPeople(roomPeople: Int): Int

    suspend fun callCreateRoom(createRoomRequest: CreateRoomRequest): Resource<BaseResponse>

}
