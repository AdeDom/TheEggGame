package com.adedom.theegggame.ui.main

import com.adedom.theegggame.util.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    fun getPlayers(playerId: String) = playerRepository.getPlayers(playerId)

    fun getPlayerRank(search: String, limit: String) = playerRepository.getPlayerRank(search, limit)

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        playerRepository.updatePassword(playerId, oldPassword, newPassword)

    fun insertLogs(randomKey: String, dateIn: String, timeIn: String, playerId: String) =
        baseRepository.insertLogs(randomKey, dateIn, timeIn, playerId)
}