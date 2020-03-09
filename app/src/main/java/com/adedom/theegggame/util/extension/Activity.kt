package com.adedom.theegggame.util.extension

import android.app.Activity
import android.content.Intent
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.util.KEY_PLAYER_ID
import com.adedom.theegggame.util.KEY_STRING
import com.adedom.theegggame.util.KEY_USERNAME

fun Activity.loginSuccess(
    c: Class<*>,
    playerId: String = KEY_EMPTY,
    username: String = KEY_STRING
) {
    writePrefFile(KEY_PLAYER_ID, playerId)
    writePrefFile(KEY_USERNAME, username)
    this.finishAffinity()
    this.startActivity(Intent(baseContext, c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

