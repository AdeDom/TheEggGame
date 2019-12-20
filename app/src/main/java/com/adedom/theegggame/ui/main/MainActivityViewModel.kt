package com.adedom.theegggame.ui.main

import com.adedom.theegggame.util.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    fun getPlayers(playerId: String) = playerRepository.getPlayers(playerId)

    fun insertLogs(randomKey: String, dateIn: String, timeIn: String, playerId: String) =
        baseRepository.insertLogs(randomKey, dateIn, timeIn, playerId)
}