package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.ItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse

interface SingleUseCase {

    suspend fun callItemCollection(itemCollectionRequest: ItemCollectionRequest): Resource<BaseResponse>

}
