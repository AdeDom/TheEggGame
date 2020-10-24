package com.adedom.teg.presentation.mission

import com.adedom.teg.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MissionViewModel : BaseViewModel<MissionState>(MissionState()) {

    fun callFetchMission() {
        launch {
            setState { copy(loading = true) }

            delay(3_000)
            setState { copy(isMissionDelivery = true) }

            setState { copy(loading = false) }
        }
    }

    fun callMissionDelivery() {
        launch {
            setState { copy(loading = true) }

            delay(3_000)
            setState { copy(isMissionDelivery = false) }

            setState { copy(loading = false) }
        }
    }

}
