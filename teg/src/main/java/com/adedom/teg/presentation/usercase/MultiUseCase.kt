package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse

interface MultiUseCase {

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse>

}
