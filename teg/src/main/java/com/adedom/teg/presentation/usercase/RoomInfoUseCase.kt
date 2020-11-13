package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers

interface RoomInfoUseCase {

    suspend fun callLeaveRoomInfo(): Resource<BaseResponse>

    fun isValidateRoleHead(playerId: String?, roomInfoPlayers: List<RoomInfoPlayers>?): Boolean

    suspend fun callChangeTeam(team: String): Resource<BaseResponse>

    suspend fun callChangeGoTeg(roomInfoPlayers: List<RoomInfoPlayers>?): Resource<BaseResponse>

    suspend fun callChangeStatusUnready(): Resource<BaseResponse>

}
