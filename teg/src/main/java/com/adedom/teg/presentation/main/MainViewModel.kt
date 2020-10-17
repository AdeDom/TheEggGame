package com.adedom.teg.presentation.main

import androidx.lifecycle.LiveData
import com.adedom.teg.presentation.usercase.MainUseCase
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
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

    fun callPlayerState(state: String) {
        launch {
            when (val resource = repository.callPlayerState(state)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun signOut() {
        launch {
            val signOut = useCase.signOut()
            if (!signOut) signOut()
        }
    }

}
