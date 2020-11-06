package com.adedom.teg.presentation.usercase

interface CreateRoomUseCase {

    fun validateMaxRoomPeople(roomPeople: Int): Int

    fun validateMinRoomPeople(roomPeople: Int): Int

}
