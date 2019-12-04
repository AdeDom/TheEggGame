package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class RegisterDialogViewModel(private val repository: PlayerRepository) : ViewModel() { // 2/12/19

    fun registerPlayer(
        username: String,
        password: String,
        name: String,
        image: String
    ): LiveData<Player> {
        return repository.registerPlayer(username, password, name, image)
    }
}
