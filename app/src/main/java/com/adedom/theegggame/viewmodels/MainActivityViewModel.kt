package com.adedom.theegggame.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.repositories.PlayerRepository

class MainActivityViewModel : ViewModel() {

    val repository = PlayerRepository()

    val player: LiveData<Player> = repository.player

}