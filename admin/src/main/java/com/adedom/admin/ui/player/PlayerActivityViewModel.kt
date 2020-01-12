package com.adedom.admin.ui.player

import com.adedom.admin.util.BaseViewModel

class PlayerActivityViewModel : BaseViewModel() {

    fun getPlayers(
        name: String,
        levelStart: String,
        levelEnd: String,
        online: Boolean,
        offline: Boolean,
        male: Boolean,
        female: Boolean
    ) = repository.getPlayers(name, levelStart, levelEnd, online, offline, male, female)
}
