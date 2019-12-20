package com.adedom.admin.ui.player

import com.adedom.admin.util.BaseViewModel

class PlayerActivityViewModel : BaseViewModel() {

    fun getPlayers(name: String, level: String, online: Boolean, offline: Boolean) =
        repository.getPlayers(name,level, online, offline)
}
