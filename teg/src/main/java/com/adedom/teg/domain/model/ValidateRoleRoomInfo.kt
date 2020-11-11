package com.adedom.teg.domain.model

import com.adedom.teg.models.websocket.RoomInfoPlayers

data class ValidateRoleRoomInfo(
    var playerId: String? = null,
    var roomInfoPlayers: List<RoomInfoPlayers>? = emptyList(),
)
