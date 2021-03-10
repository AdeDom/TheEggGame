package com.adedom.teg.domain.usecase

import com.adedom.teg.data.models.MultiItemDb
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.request.AddMultiScoreRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.response.FetchMultiPlayerResponse
import com.adedom.teg.models.response.MultiItemResponse
import com.adedom.teg.models.response.ScoreResponse
import com.adedom.teg.presentation.multi.TegMultiPlayerListener
import com.adedom.teg.presentation.usercase.MultiUseCase
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MultiUseCaseImpl(
    private val repository: DefaultTegRepository,
) : MultiUseCase {

    override suspend fun callTimerTegMultiPlayer(listener: TegMultiPlayerListener?): Resource<FetchMultiPlayerResponse> {
        val resource = repository.callFetchMultiPlayer()

        if (resource is Resource.Success) {
            if (resource.data.success) {
                val startTime = resource.data.roomInfoTitle?.startTime ?: 0L // real
                val endTime: Long = startTime + (1_000 * 60 * 15) // real

                val delayTime = System.currentTimeMillis() - startTime // time call api

                var tempTime = (1_000 * 60 * 15) - delayTime
                while (System.currentTimeMillis() < endTime) {
                    val timer = SimpleDateFormat("mm:ss", Locale.getDefault()).format(tempTime)
                    listener?.onTimerTegMultiPlayer(timer)
                    tempTime -= 1_000
                    delay(1_000)
                }

                listener?.onEndTegMultiPlayer()
            }
        }

        return resource
    }

    override suspend fun callFetchMultiScore(): Resource<ScoreResponse> {
        return repository.callFetchMultiScore()
    }

    override suspend fun callAddMultiScore(addMultiScoreRequest: AddMultiScoreRequest): Resource<BaseResponse> {
        val response = repository.callAddMultiScore(addMultiScoreRequest)

        if (response is Resource.Success) {
            if (response.data.success) {
                repository.outgoingMultiPlayerItems()
                repository.outgoingMultiPlayerScore()
            }
        }

        when (val resource = repository.callFetchMultiScore()) {
            is Resource.Success -> {
                val data = resource.data
                if (data.success) {
                    val sumScore = (data.score?.scoreTeamA ?: 0) + (data.score?.scoreTeamB ?: 0)
                    if (sumScore >= 5) {
                        repository.outgoingMultiPlayerEndGame()
                    }
                }
            }
        }

        return response
    }

    override suspend fun callFetchMultiItem(): Resource<MultiItemResponse> {
        return repository.callFetchMultiItem()
    }

    override suspend fun callAddMultiItem(): Resource<BaseResponse> {
        val response = repository.callAddMultiItem()

        if (response is Resource.Success) {
            if (response.data.success) {
                repository.outgoingMultiPlayerItems()
            }
        }

        return response
    }

    override suspend fun callMultiPlayerEndGame(): Resource<BaseResponse> {
        return repository.callMultiPlayerEndGame()
    }

    override fun isValidateDistanceBetween(
        latLng: TegLatLng,
        multiItems: List<MultiItemDb>?
    ): Boolean {
        val count = multiItems
            ?.map { distanceBetween(latLng, TegLatLng(it.latitude, it.longitude)) }
            ?.filter { it < 100 }
            ?.count()

        return count != 0
    }

    private fun distanceBetween(startP: TegLatLng, endP: TegLatLng): Double {
        val lat1: Double = startP.latitude
        val lat2: Double = endP.latitude
        val lon1: Double = startP.longitude
        val lon2: Double = endP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * asin(sqrt(a))
        return 6366000 * c
    }

    override fun getMultiItemId(latLng: TegLatLng?, multiItems: List<MultiItemDb>?): Int? {
        return latLng?.let {
            multiItems
                ?.asSequence()
                ?.map {
                    Pair(
                        it,
                        distanceBetween(latLng, TegLatLng(it.latitude, it.longitude))
                    )
                }
                ?.filter { it.second < 100 }
                ?.map { it.first.multiId }
                ?.take(1)
                ?.singleOrNull()
        }
    }

}
