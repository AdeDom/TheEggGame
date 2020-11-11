package com.adedom.teg.domain.usecase

import com.adedom.teg.data.network.websocket.RoomInfoPlayersSocket
import com.adedom.teg.data.network.websocket.RoomInfoTitleSocket
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers
import com.adedom.teg.presentation.usercase.RoomInfoUseCase
import com.adedom.teg.util.TegConstant

class RoomInfoUseCaseImpl(
    private val repository: DefaultTegRepository,
) : RoomInfoUseCase {

    override suspend fun incomingRoomInfoTitle(roomNo: String?, socket: RoomInfoTitleSocket) {
        repository.incomingRoomInfoTitle { roomInfoTitleOutgoing ->
            if (roomNo == roomInfoTitleOutgoing.roomNo) {
                socket.invoke(roomInfoTitleOutgoing)
            }
        }
    }

    override suspend fun incomingRoomInfoPlayers(roomNo: String?, socket: RoomInfoPlayersSocket) {
        repository.incomingRoomInfoPlayers { roomInfoPlayersOutgoing ->
            if (roomNo == roomInfoPlayersOutgoing.roomNo) {
                socket.invoke(roomInfoPlayersOutgoing)
            }
        }
    }

    override suspend fun callLeaveRoomInfo(): Resource<BaseResponse> {
        val resource = repository.callLeaveRoomInfo()

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingRoomInfoPlayers()
                    repository.outgoingPlaygroundRoom()
                }
            }
        }

        return resource
    }

    override fun isValidateRoleHead(
        playerId: String?,
        roomInfoPlayers: List<RoomInfoPlayers>?
    ): Boolean {
        val roomInfoPlayer = roomInfoPlayers?.single { it.playerId == playerId }

        return roomInfoPlayer?.roleRoomInfo == TegConstant.ROOM_ROLE_HEAD
    }

    override suspend fun callChangeTeam(team: String): Resource<BaseResponse> {
        val resource = repository.callChangeTeam(team)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingRoomInfoPlayers()
                }
            }
        }

        return resource
    }

    override suspend fun callChangeGoTeg(roomInfoPlayers: List<RoomInfoPlayers>?): Resource<BaseResponse> {
        val playerId = repository.getDbPlayerInfo()?.playerId

        return if (isValidateRoleHead(playerId, roomInfoPlayers)) {
            changeStatusReadyHead()
        } else {
            changeStatusReadyTail()
        }
    }

    private suspend fun changeStatusReadyHead(): Resource<BaseResponse> {
        return changeStatusReadyTail()
    }

    private suspend fun changeStatusReadyTail(): Resource<BaseResponse> {
        val resource = repository.callChangeGoTeg()

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingRoomInfoPlayers()
                }
            }
        }

        return resource
    }

}
