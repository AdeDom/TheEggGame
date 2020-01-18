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

    companion object {
        var name = ""
        var spinnerIndexStart = 0
        var spinnerIndexEnd = 98
        var isCheckOnline = true
        var isCheckOffline = true
        var isCheckMale = true
        var isCheckFemale = true
    }
}
