package com.adedom.admin.ui.player

import com.adedom.admin.util.BaseViewModel
import com.adedom.admin.util.KEY_STRING

class PlayerActivityViewModel : BaseViewModel() {

    fun getPlayers(
        name: String,
        levelStart: Int,
        levelEnd: Int,
        online: Boolean,
        offline: Boolean,
        male: Boolean,
        female: Boolean
    ) = repository.getPlayers(name, levelStart, levelEnd, online, offline, male, female)

    companion object {
        var name = KEY_STRING
        var spinnerIndexStart = 0
        var spinnerIndexEnd = 98
        var isCheckOnline = true
        var isCheckOffline = true
        var isCheckMale = true
        var isCheckFemale = true
    }
}
