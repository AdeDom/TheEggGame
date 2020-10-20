package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ChangeProfileModel
import com.adedom.teg.models.response.BaseResponse
import java.util.*

interface ChangeProfileUseCase {

    suspend fun callChangeProfile(changeProfile: ChangeProfileModel): Resource<BaseResponse>

    fun getStringBirthDate(birthDate: Calendar?): String

}
