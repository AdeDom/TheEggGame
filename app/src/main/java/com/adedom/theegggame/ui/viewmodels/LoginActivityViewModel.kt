package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class LoginActivityViewModel(private val repository: PlayerRepository) : ViewModel() { // 2/12/19

    fun getPlayerId(username: String, password: String): LiveData<Player> {
        return repository.getPlayerId(username, password)
    }

}