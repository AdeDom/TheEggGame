package com.adedom.theegggame.util.extension

import android.app.Activity
import android.content.Intent
import com.adedom.library.extension.setPrefFile
import com.adedom.utility.EMPTY
import com.adedom.utility.PLAYER_ID
import com.adedom.utility.USERNAME

fun Activity.login(
    c: Class<*>,
    playerId: String = EMPTY,
    username: String = ""
) {
    setPrefFile(PLAYER_ID, playerId)
    setPrefFile(USERNAME, username)
    this.finishAffinity()
    this.startActivity(Intent(baseContext, c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

