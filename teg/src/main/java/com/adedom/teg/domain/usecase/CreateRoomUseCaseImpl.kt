package com.adedom.teg.domain.usecase

import com.adedom.teg.presentation.usercase.CreateRoomUseCase
import com.adedom.teg.util.TegConstant

class CreateRoomUseCaseImpl : CreateRoomUseCase {

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

}
