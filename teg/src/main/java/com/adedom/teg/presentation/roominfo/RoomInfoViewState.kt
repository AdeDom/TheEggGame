package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.websocket.RoomInfoOutgoing

data class RoomInfoViewState(
    val roomInfoOutgoing: RoomInfoOutgoing? = null,
    val loading: Boolean = false,
)
