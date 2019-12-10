package com.adedom.theegggame.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.networks.BaseApi
import com.adedom.theegggame.data.repositories.BaseRepository
import com.adedom.theegggame.data.repositories.PlayerRepository

class MainActivityViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun getPlayers(playerId: String): LiveData<Player> {
        return repository.getPlayers(playerId)
    }

    fun insertLogs(
        randomKey: String,
        dateIn: String,
        timeIn: String,
        playerId: String
    ): LiveData<JsonResponse> {
        return BaseRepository(BaseApi()).insertLogs(randomKey, dateIn, timeIn, playerId)
    }

}