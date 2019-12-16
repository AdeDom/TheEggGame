package com.adedom.admin.ui.player

import com.adedom.admin.util.BaseViewModel

class PlayerActivityViewModel : BaseViewModel() {

    fun getPlayers(search: String, level: String, online: Boolean, offline: Boolean) =
        repository.getPlayers(search,level, online, offline)
}
