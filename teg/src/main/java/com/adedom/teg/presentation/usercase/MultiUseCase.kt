package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.response.FetchMultiPlayerResponse
import com.adedom.teg.models.response.MultiItemResponse
import com.adedom.teg.models.response.ScoreResponse
import com.adedom.teg.presentation.multi.TegMultiPlayerListener

interface MultiUseCase {

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse>

    suspend fun callTimerTegMultiPlayer(listener: TegMultiPlayerListener?): Resource<FetchMultiPlayerResponse>

    suspend fun callFetchMultiScore(): Resource<ScoreResponse>

    suspend fun callAddMultiScore(): Resource<BaseResponse>

    suspend fun callFetchMultiItem(): Resource<MultiItemResponse>

    suspend fun callAddMultiItem(): Resource<BaseResponse>

}
