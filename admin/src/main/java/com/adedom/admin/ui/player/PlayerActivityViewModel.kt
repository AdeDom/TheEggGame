package com.adedom.admin.ui.player

import com.adedom.admin.util.BaseViewModel

class PlayerActivityViewModel : BaseViewModel() {

    fun getPlayers(search: String) = repository.getPlayers(search)
}
