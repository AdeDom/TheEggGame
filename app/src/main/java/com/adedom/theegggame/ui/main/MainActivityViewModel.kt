package com.adedom.theegggame.ui.main

import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.getDateTime
import com.adedom.library.util.getKeyUUID
import com.adedom.theegggame.util.*

class MainActivityViewModel : BaseViewModel() {

    fun getPlayer(playerId: String) = playerRepository.getPlayer(playerId)

    fun rank(search: String, limit: String) = playerRepository.getPlayers(search, limit)

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        playerRepository.updatePassword(playerId, oldPassword, newPassword)

    fun missionComplete(
        playerId: String?,
        itemId: Int = 1,
        qty: Int = 300,
        latitude: Double = 19.116585,
        longitude: Double = 99.847193
    ) = singleRepository.insertItemCollection(playerId, itemId, qty, latitude, longitude)

    fun insertLogs(key: String, playerId: String) =
        baseRepository.insertLogs(key, playerId)

    fun writeFile() {
        if (GameActivity.sContext.readPrefFile(SOUND_MUSIC) == "")
            GameActivity.sContext.writePrefFile(SOUND_MUSIC, SOUND_MUSIC_ON)

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DATE) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_DATE, getDateTime(KEY_DATE))

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DELIVERY) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_UNSUCCESSFUL)

        //delivery
        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DATE) != getDateTime(KEY_DATE)) {
            GameActivity.sContext.writePrefFile(KEY_MISSION_DATE, getDateTime(KEY_DATE))
            GameActivity.sContext.writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_UNSUCCESSFUL)
        }

    }

    companion object {
        var keyLogs: String = getKeyUUID()
        var timeStamp: Long = System.currentTimeMillis() / 1000
    }

}

