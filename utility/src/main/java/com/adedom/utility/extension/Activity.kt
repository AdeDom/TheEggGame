package com.adedom.utility.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.adedom.utility.*

fun Activity.login(
    c: Class<*>,
    playerId: String = EMPTY,
    username: String? = null
) {
    baseContext.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit()
        .putString(PLAYER_ID, playerId)
        .putString(USERNAME, username)
        .apply()
    this.finishAffinity()
    this.startActivity(Intent(baseContext, c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun Activity.exitMain() {
    if (countExit > 0) this.finishAffinity()
    countExit++
    Handler().postDelayed({ countExit = 0 }, 2000)
    baseContext.toast(R.string.on_back_pressed)
}
