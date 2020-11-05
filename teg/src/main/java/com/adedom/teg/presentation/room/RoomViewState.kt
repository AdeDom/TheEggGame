package com.adedom.teg.presentation.room

import com.adedom.teg.models.response.FetchRoomResponse

data class RoomViewState(
    val rooms: List<FetchRoomResponse> = emptyList(),
    val peopleAll: Int = 0,
    val loading: Boolean = false,
)
