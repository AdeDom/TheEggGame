package com.adedom.theegggame.ui.main

import com.adedom.theegggame.util.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    fun getPlayer(playerId: String) = playerRepository.getPlayer(playerId)

    fun rank(search: String, limit: String) = playerRepository.getPlayers(search, limit)

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        playerRepository.updatePassword(playerId, oldPassword, newPassword)

    fun insertLogs(key: String, dateIn: String, timeIn: String, playerId: String) =
        baseRepository.insertLogs(key, dateIn, timeIn, playerId)
}

