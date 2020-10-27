package com.adedom.teg.presentation.playerprofile

import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.repository.DefaultTegRepository

class PlayerProfileViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<PlayerProfileViewState>(PlayerProfileViewState) {

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

}
