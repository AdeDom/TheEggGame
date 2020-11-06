package com.adedom.teg.presentation.createroom

import com.adedom.teg.util.TegConstant

data class CreateRoomViewState(
    val roomName: String = "",
    val roomPeople: Int = TegConstant.ROOM_PEOPLE_MIN,
    val loading: Boolean = false,
)
