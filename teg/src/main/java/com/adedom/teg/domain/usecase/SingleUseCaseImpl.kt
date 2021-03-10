package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.models.SingleItemDb
import com.adedom.teg.data.network.websocket.PlaygroundSinglePlayerSocket
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.response.BackpackResponse
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.websocket.PlaygroundSinglePlayerOutgoing
import com.adedom.teg.models.websocket.SingleItemOutgoing
import com.adedom.teg.presentation.usercase.SingleItemAroundSocket
import com.adedom.teg.presentation.usercase.SingleUseCase
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SingleUseCaseImpl(
    private val repository: DefaultTegRepository,
) : SingleUseCase {

    override suspend fun incomingSingleItem(latLng: TegLatLng, socket: SingleItemAroundSocket) {
        repository.incomingSingleItem { singleItemOutgoing ->
            val singleItemAround = singleItemOutgoing.singleItems
                .map { Pair(it, distanceBetween(latLng, TegLatLng(it.latitude, it.longitude))) }
                .filter { it.second < 3_000 }
                .map { it.first }

            socket.invoke(SingleItemOutgoing(singleItemAround))
        }
    }

    override suspend fun incomingPlaygroundSinglePlayer(
        latLng: TegLatLng,
        socket: PlaygroundSinglePlayerSocket
    ) {
        repository.incomingPlaygroundSinglePlayer { playgroundSinglePlayerOutgoing ->
            val players = playgroundSinglePlayerOutgoing.players.filter {
                it.playerId != repository.getDbPlayerInfo()?.playerId
            }.filter { it.latitude != null && it.longitude != null }
                .map { Pair(it, distanceBetween(latLng, TegLatLng(it.latitude!!, it.longitude!!))) }
                .filter { it.second < 3_000 }
                .map { it.first }.toList()

            socket.invoke(PlaygroundSinglePlayerOutgoing(players))
        }
    }

    override suspend fun callFetchItemCollection(): Resource<BackpackResponse> {
        val resource = repository.callFetchItemCollection()

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    val backpack = resource.data.backpack
                    val backpackEntity = BackpackEntity(
                        eggI = backpack?.eggI,
                        eggII = backpack?.eggII,
                        eggIII = backpack?.eggIII,
                    )
                    repository.saveBackpack(backpackEntity)
                }
            }
        }

        return resource
    }

    override suspend fun callSingleItemCollection(singleId: Int?): Resource<BaseResponse> {
        val resource = repository.callSingleItemCollection(singleId)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    repository.outgoingSingleItem()
                    repository.outgoingSingleSuccessAnnouncement()
                    fetchItemCollection()
                }
            }
        }

        return resource
    }

    private suspend fun fetchItemCollection() {
        when (val resource = repository.callFetchItemCollection()) {
            is Resource.Success -> {
                if (resource.data.success) {
                    // save backpack
                    val backpack = resource.data.backpack
                    val backpackEntity = BackpackEntity(
                        eggI = backpack?.eggI,
                        eggII = backpack?.eggII,
                        eggIII = backpack?.eggIII,
                    )
                    repository.saveBackpack(backpackEntity)

                    // fetch player info
                    fetchPlayerInfo()
                }
            }
        }
    }

    private suspend fun fetchPlayerInfo() {
        when (val resource = repository.callFetchPlayerInfo()) {
            is Resource.Success -> {
                if (resource.data.success) {
                    val playerInfo = resource.data.playerInfo
                    val playerInfoEntity = PlayerInfoEntity(
                        playerId = playerInfo?.playerId.orEmpty(),
                        username = playerInfo?.username,
                        name = playerInfo?.name,
                        image = playerInfo?.image,
                        level = playerInfo?.level,
                        state = playerInfo?.state,
                        gender = playerInfo?.gender,
                        birthDate = playerInfo?.birthDate,
                    )
                    repository.savePlayerInfo(playerInfoEntity)
                }
            }
            is Resource.Error -> fetchPlayerInfo()
        }
    }

    override fun isValidateDistanceBetween(
        latLng: TegLatLng,
        singleItems: List<SingleItemDb>?
    ): Boolean {
        val count = singleItems
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

    override fun getSingleItemId(latLng: TegLatLng?, singleItems: List<SingleItemDb>?): Int? {
        return latLng?.let {
            singleItems
                ?.asSequence()
                ?.map {
                    Pair(
                        it,
                        distanceBetween(latLng, TegLatLng(it.latitude, it.longitude))
                    )
                }
                ?.filter { it.second < 100 }
                ?.map { it.first.singleId }
                ?.take(1)
                ?.singleOrNull()
        }
    }

}
