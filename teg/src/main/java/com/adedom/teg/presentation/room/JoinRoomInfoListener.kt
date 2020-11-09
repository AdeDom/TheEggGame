package com.adedom.teg.presentation.room

import com.adedom.teg.models.response.BaseResponse

interface JoinRoomInfoListener {
    fun onJoinRoomInfoResponse(response: BaseResponse)
}
