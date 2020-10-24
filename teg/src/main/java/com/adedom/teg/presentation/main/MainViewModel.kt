package com.adedom.teg.presentation.main

import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.MainUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: MainUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<MainState>(MainState()) {

    val playerInfo: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    fun fetchPlayerInfo() {
        launch {
            setState { copy(loading = true) }
            val callApi = useCase.fetchPlayerInfo()
            if (!callApi) fetchPlayerInfo()
            setState { copy(loading = false) }
        }
    }

    fun callPlayerStateOnline() {
        launch {
            when (val resource = repository.callPlayerState(TegConstant.STATE_ONLINE)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun callPlayerStateOffline() {
        launch {
            useCase.callPlayerStateOffline()
        }
    }

    fun callLogActiveOn() {
        launch {
            when (val resource = repository.callLogActiveOn()) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun callLogActiveOff() {
        launch {
            useCase.callLogActiveOff()
        }
    }

}
