package com.adedom.theegggame.ui.dialogs.registerplayer

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.PlayerRepository

class RegisterPlayerDialogViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String,
        date: String,
        time: String
    ) = repository.insertPlayer(username, password, name, image, date, time)
}
