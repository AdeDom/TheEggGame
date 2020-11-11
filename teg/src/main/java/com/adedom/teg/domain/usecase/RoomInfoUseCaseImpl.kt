package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateRoleRoomInfo
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
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

    override fun isValidateRoleHead(validateRoleRoomInfo: ValidateRoleRoomInfo): Boolean {
        val (playerId, roomInfoPlayers) = validateRoleRoomInfo

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

    override suspend fun callChangeGoTeg(validateRoleRoomInfo: ValidateRoleRoomInfo): Resource<BaseResponse> {
        return if (isValidateRoleHead(validateRoleRoomInfo)) {
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
