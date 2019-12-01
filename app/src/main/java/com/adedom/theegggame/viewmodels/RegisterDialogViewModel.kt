package com.adedom.theegggame.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.repositories.PlayerRepository

class RegisterDialogViewModel : ViewModel() {

    val repository = PlayerRepository()

    val result: LiveData<Player> = repository.player

}
