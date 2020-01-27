package com.adedom.theegggame.ui.main

import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.getDateTime
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
        latitude: Double = 0.0,
        longitude: Double = 0.0
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

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_SINGLE) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_SINGLE, KEY_MISSION_UNSUCCESSFUL)

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_SINGLE_GAME) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_SINGLE_GAME, KEY_MISSION_UNSUCCESSFUL)

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_MULTI) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_MULTI, KEY_MISSION_UNSUCCESSFUL)

        if (GameActivity.sContext.readPrefFile(KEY_MISSION_MULTI_GAME) == "")
            GameActivity.sContext.writePrefFile(KEY_MISSION_MULTI_GAME, KEY_MISSION_UNSUCCESSFUL)

        //delivery
        if (GameActivity.sContext.readPrefFile(KEY_MISSION_DATE) != getDateTime(KEY_DATE)) {
            GameActivity.sContext.writePrefFile(KEY_MISSION_DATE, getDateTime(KEY_DATE))
            GameActivity.sContext.writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_UNSUCCESSFUL)
            GameActivity.sContext.writePrefFile(KEY_MISSION_SINGLE, KEY_MISSION_UNSUCCESSFUL)
            GameActivity.sContext.writePrefFile(KEY_MISSION_SINGLE_GAME, KEY_MISSION_UNSUCCESSFUL)
            GameActivity.sContext.writePrefFile(KEY_MISSION_MULTI, KEY_MISSION_UNSUCCESSFUL)
            GameActivity.sContext.writePrefFile(KEY_MISSION_MULTI_GAME, KEY_MISSION_UNSUCCESSFUL)
        }

    }

}

