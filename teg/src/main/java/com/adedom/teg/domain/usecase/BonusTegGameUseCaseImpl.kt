package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.BonusTegGameUseCase

class BonusTegGameUseCaseImpl(
    private val repository: DefaultTegRepository,
) : BonusTegGameUseCase {

    override suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse> {
        val resource = repository.callMultiItemCollection(multiItemCollectionRequest)

        if (resource is Resource.Success) {
            if (resource.data.success) {
                fetchPlayerInfo()
            }
        }

        return resource
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

    override fun calBonusTegGame(degree: Float): Int {
        return when {
            degree >= 1 && degree < 90 -> 0
            degree >= 90 && degree < 180 -> 300
            degree >= 180 && degree < 270 -> 400
            degree >= 270 && degree < 360 -> 500
            else -> 0
        }
    }

}
