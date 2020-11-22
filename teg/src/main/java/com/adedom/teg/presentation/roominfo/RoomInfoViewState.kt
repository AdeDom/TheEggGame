package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.response.FetchRoomResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers

data class RoomInfoViewState(
    var roomInfoTitle: FetchRoomResponse? = null,
    var roomInfoPlayers: List<RoomInfoPlayers> = emptyList(),
    var isRoleHead: Boolean = false,
    val isClickable: Boolean = true,
    val loading: Boolean = false,
)
