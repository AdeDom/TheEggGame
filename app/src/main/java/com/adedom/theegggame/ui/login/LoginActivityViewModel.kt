package com.adedom.theegggame.ui.login

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.PlayerRepository

class LoginActivityViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun getPlayerIdLogin(username: String, password: String) =
        repository.getPlayerIdLogin(username, password)
}