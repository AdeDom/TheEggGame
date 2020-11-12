package com.adedom.teg.presentation.usercase

import com.adedom.teg.data.network.websocket.RoomInfoPlayersSocket
import com.adedom.teg.data.network.websocket.RoomInfoTegMultiSocket
import com.adedom.teg.data.network.websocket.RoomInfoTitleSocket
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers

interface RoomInfoUseCase {

    suspend fun incomingRoomInfoTitle(roomNo: String?, socket: RoomInfoTitleSocket)

    suspend fun incomingRoomInfoPlayers(roomNo: String?, socket: RoomInfoPlayersSocket)

    suspend fun incomingRoomInfoTegMulti(roomNo: String?, socket: RoomInfoTegMultiSocket)

    suspend fun callLeaveRoomInfo(): Resource<BaseResponse>

    fun isValidateRoleHead(playerId: String?, roomInfoPlayers: List<RoomInfoPlayers>?): Boolean

    suspend fun callChangeTeam(team: String): Resource<BaseResponse>

    suspend fun callChangeGoTeg(roomInfoPlayers: List<RoomInfoPlayers>?): Resource<BaseResponse>

    suspend fun callChangeStatusUnready(): Resource<BaseResponse>

}
