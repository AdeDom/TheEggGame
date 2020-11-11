package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.response.FetchRoomResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers

data class RoomInfoViewState(
    var playerId: String? = null,
    var roomNo: String? = null,
    var roomInfoTitle: FetchRoomResponse? = null,
    var roomInfoPlayers: List<RoomInfoPlayers> = emptyList(),
    var isRoleHead: Boolean = false,
    val loading: Boolean = false,
)
