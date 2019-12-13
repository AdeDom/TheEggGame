package com.adedom.admin.ui.player

import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.util.BaseViewModel

class PlayerActivityViewModel(repository: BaseRepository) :
    BaseViewModel<BaseRepository>(repository) {

    fun getPlayers() = repository.getPlayers()

}
