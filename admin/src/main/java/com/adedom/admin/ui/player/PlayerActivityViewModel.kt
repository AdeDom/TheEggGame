package com.adedom.admin.ui.player

import androidx.lifecycle.ViewModel
import com.adedom.admin.data.repositories.BaseRepository

class PlayerActivityViewModel(private val repository: BaseRepository) : ViewModel() {

    fun getPlayers(search: String) = repository.getPlayers(search)

}
