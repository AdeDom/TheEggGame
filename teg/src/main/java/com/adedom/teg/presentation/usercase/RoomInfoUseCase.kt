package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateRoleRoomInfo
import com.adedom.teg.models.response.BaseResponse

interface RoomInfoUseCase {

    suspend fun callLeaveRoomInfo(): Resource<BaseResponse>

    fun isValidateRoleHead(validateRoleRoomInfo: ValidateRoleRoomInfo): Boolean

    suspend fun callChangeTeam(team: String): Resource<BaseResponse>

    suspend fun callChangeGoTeg(validateRoleRoomInfo: ValidateRoleRoomInfo): Resource<BaseResponse>

}
