package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.CreateRoomRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.CreateRoomUseCase
import com.adedom.teg.util.TegConstant

class CreateRoomUseCaseImpl(
    private val repository: DefaultTegRepository,
) : CreateRoomUseCase {

    override fun validateMaxRoomPeople(roomPeople: Int): Int {
        return if (roomPeople >= TegConstant.ROOM_PEOPLE_MAX) {
            roomPeople
        } else {
            roomPeople.plus(1)
        }
    }

    override fun validateMinRoomPeople(roomPeople: Int): Int {
        return if (roomPeople <= TegConstant.ROOM_PEOPLE_MIN) {
            roomPeople
        } else {
            roomPeople.minus(1)
        }
    }

    override suspend fun callCreateRoom(createRoomRequest: CreateRoomRequest): Resource<BaseResponse> {
        val resource = repository.callCreateRoom(createRoomRequest)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingPlaygroundRoom()
                }
            }
        }

        return resource
    }

}
