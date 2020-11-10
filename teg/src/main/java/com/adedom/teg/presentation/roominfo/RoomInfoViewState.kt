package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.response.FetchRoomResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers

data class RoomInfoViewState(
    var roomNo: String? = null,
    var roomInfoTitle: FetchRoomResponse? = null,
    var roomInfoPlayers: List<RoomInfoPlayers> = emptyList(),
    val loading: Boolean = false,
)
