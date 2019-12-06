package com.adedom.theegggame.ui.dialogs.registerplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class RegisterPlayerDialogViewModel(private val repository: PlayerRepository) : ViewModel() { // 2/12/19

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String
    ): LiveData<Player> {
        return repository.insertPlayer(username, password, name, image)
    }
}
