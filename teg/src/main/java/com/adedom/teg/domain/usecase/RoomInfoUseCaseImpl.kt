package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.RoomInfoPlayers
import com.adedom.teg.presentation.usercase.RoomInfoUseCase
import com.adedom.teg.util.TegConstant

class RoomInfoUseCaseImpl(
    private val repository: DefaultTegRepository,
) : RoomInfoUseCase {

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
        val resource = repository.callRoomInfoTegMulti()

        when (resource) {
            is Resource.Success -> {
                repository.outgoingRoomInfoPlayers()
                if (resource.data.success) {
                    repository.outgoingRoomInfoTegMulti()
                    repository.outgoingPlaygroundRoom()
                }
            }
        }

        return resource
    }

    private suspend fun changeStatusReadyTail(): Resource<BaseResponse> {
        val resource = repository.callChangeStatusRoomInfo()

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingRoomInfoPlayers()
                }
            }
        }

        return resource
    }

    override suspend fun callChangeStatusUnready(): Resource<BaseResponse> {
        val resource = repository.callChangeStatusUnready()

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
