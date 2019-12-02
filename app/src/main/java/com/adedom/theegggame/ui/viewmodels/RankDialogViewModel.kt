package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class RankDialogViewModel : ViewModel() { // 2/12/19

    val repository = PlayerRepository()

    fun getPlayerRank(): LiveData<List<Player>> = repository.players

}
