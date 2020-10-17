package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.MainUseCase
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.sharedpreference.service.SessionManagerService

class MainUseCaseImpl(
    private val repository: DefaultTegRepository,
    private val sessionManagerService: SessionManagerService,
) : MainUseCase {

    override suspend fun fetchPlayerInfo(): Boolean {
        if (repository.getDbPlayerInfo() == null) {
            val resource = repository.callFetchPlayerInfo()

            if (resource is Resource.Success) {
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
        return true
    }

    override suspend fun signOut(): Boolean {
        sessionManagerService.accessToken = ""
        sessionManagerService.refreshToken = ""
        repository.deletePlayerInfo()
        return true
    }

}
