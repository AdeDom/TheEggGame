package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.ChangeProfileRequest
import com.adedom.teg.models.response.BaseResponse

interface ChangeProfileUseCase {

    suspend fun callChangeProfile(changeProfile: ChangeProfileRequest): Resource<BaseResponse>

}
