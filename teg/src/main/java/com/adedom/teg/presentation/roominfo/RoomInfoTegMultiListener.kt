package com.adedom.teg.presentation.roominfo

import com.adedom.teg.models.websocket.RoomInfoTegMultiOutgoing

interface RoomInfoTegMultiListener {
    fun roomInfoTegMultiResponse(roomInfoTegMultiOutgoing: RoomInfoTegMultiOutgoing)
}
