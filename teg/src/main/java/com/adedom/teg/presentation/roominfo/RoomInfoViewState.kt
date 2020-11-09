package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.response.FetchRoomResponse
import com.adedom.teg.models.response.PlayerInfo

data class RoomInfoViewState(
    var roomInfoTitle: FetchRoomResponse? = null,
    var roomInfoPlayers: List<PlayerInfo> = emptyList(),
    val loading: Boolean = false,
)
