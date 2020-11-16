package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.SingleUseCase

class SingleUseCaseImpl(
    private val repository: DefaultTegRepository,
) : SingleUseCase {

    override suspend fun callSingleItemCollection(singleId: Int): Resource<BaseResponse> {
        val resource = repository.callSingleItemCollection(singleId)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
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

}
