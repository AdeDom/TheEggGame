package com.adedom.teg.presentation.usercase

import com.adedom.teg.data.models.SingleItemDb
import com.adedom.teg.data.network.websocket.PlaygroundSinglePlayerSocket
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.response.BackpackResponse
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.SingleItemOutgoing

typealias SingleItemAroundSocket = (SingleItemOutgoing) -> Unit

interface SingleUseCase {

    suspend fun incomingSingleItem(latLng: TegLatLng, socket: SingleItemAroundSocket)

    suspend fun incomingPlaygroundSinglePlayer(
        latLng: TegLatLng,
        socket: PlaygroundSinglePlayerSocket
    )

    suspend fun callFetchItemCollection(): Resource<BackpackResponse>

    suspend fun callSingleItemCollection(singleId: Int?): Resource<BaseResponse>

    fun isValidateDistanceBetween(latLng: TegLatLng, singleItems: List<SingleItemDb>?): Boolean

    fun getSingleItemId(latLng: TegLatLng?, singleItems: List<SingleItemDb>?): Int?

}
