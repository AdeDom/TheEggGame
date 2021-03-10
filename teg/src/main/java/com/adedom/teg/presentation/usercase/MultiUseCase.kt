package com.adedom.teg.presentation.usercase

import com.adedom.teg.data.models.MultiItemDb
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.request.AddMultiScoreRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.response.FetchMultiPlayerResponse
import com.adedom.teg.models.response.MultiItemResponse
import com.adedom.teg.models.response.ScoreResponse
import com.adedom.teg.presentation.multi.TegMultiPlayerListener

interface MultiUseCase {

    suspend fun callTimerTegMultiPlayer(listener: TegMultiPlayerListener?): Resource<FetchMultiPlayerResponse>

    suspend fun callFetchMultiScore(): Resource<ScoreResponse>

    suspend fun callAddMultiScore(addMultiScoreRequest: AddMultiScoreRequest): Resource<BaseResponse>

    suspend fun callFetchMultiItem(): Resource<MultiItemResponse>

    suspend fun callAddMultiItem(): Resource<BaseResponse>

    suspend fun callMultiPlayerEndGame(): Resource<BaseResponse>

    fun isValidateDistanceBetween(latLng: TegLatLng, multiItems: List<MultiItemDb>?): Boolean

    fun getMultiItemId(latLng: TegLatLng?, multiItems: List<MultiItemDb>?): Int?

}
