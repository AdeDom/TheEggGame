package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.SettingUseCase
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import com.adedom.teg.sharedpreference.service.SessionManagerService

class SettingUseCaseImpl(
    private val repository: DefaultTegRepository,
    private val sessionManagerService: SessionManagerService,
    private val preferenceConfig: PreferenceConfig,
) : SettingUseCase {

    override suspend fun signOut(): Boolean {
        sessionManagerService.accessToken = ""
        sessionManagerService.refreshToken = ""
        preferenceConfig.signOut = true
        repository.deletePlayerInfo()
        repository.deleteBackpack()
        return true
    }

}
