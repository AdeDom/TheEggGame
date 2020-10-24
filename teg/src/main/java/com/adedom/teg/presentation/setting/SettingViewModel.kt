package com.adedom.teg.presentation.setting

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.presentation.usercase.SettingUseCase
import kotlinx.coroutines.launch

class SettingViewModel(
    private val useCase: SettingUseCase,
) : BaseViewModel<SettingState>(SettingState) {

    fun signOut() {
        launch {
            val signOut = useCase.signOut()
            if (!signOut) signOut()
        }
    }

}
