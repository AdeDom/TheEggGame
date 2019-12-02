package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class RegisterDialogViewModel : ViewModel() { // 2/12/19

    val repository = PlayerRepository()

    fun registerPlayer(): LiveData<Player> = repository.player

}
