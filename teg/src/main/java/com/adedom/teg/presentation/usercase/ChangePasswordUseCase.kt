package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.ChangePasswordRequest
import com.adedom.teg.models.response.BaseResponse

interface ChangePasswordUseCase {

    suspend fun callChangePassword(changePassword: ChangePasswordRequest): Resource<BaseResponse>

}
