package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse

interface BonusTegGameUseCase {

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse>

    fun calBonusTegGame(degree: Float): Int

}
