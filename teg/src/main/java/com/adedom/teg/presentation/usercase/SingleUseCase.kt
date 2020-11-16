package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse

interface SingleUseCase {

    suspend fun callSingleItemCollection(singleId: Int): Resource<BaseResponse>

}
