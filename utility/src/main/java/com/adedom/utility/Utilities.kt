package com.adedom.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

const val PREF_LOGIN = "PREF_LOGIN"
const val PLAYER_ID = "player_id"
const val USERNAME = "username"

fun EditText.isEmpty(error: String): Boolean {
    if (this.text.toString().trim().isEmpty()) {
        this.requestFocus()
        this.error = error
        return true
    }
    return false
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.failed() {
    Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show()
}

fun Context.completed() {
    Toast.makeText(this, R.string.completed, Toast.LENGTH_SHORT).show()
}

fun Activity.login(
    c: Class<*>,
    playerId: String = "empty",
    username: String? = null
) {
    baseContext.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit()
        .putString(PLAYER_ID, playerId)
        .putString(USERNAME, username)
        .apply()
    this.finishAffinity()
    this.startActivity(Intent(baseContext, c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun Context.getPrefLogin(key: String): String {
    val preferences = getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE)
    return preferences.getString(key, "")!!
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageView.loadProfile(image: String) {
    Glide.with(this)
        .load("$BASE_URL../profiles/$image")
        .circleCrop()
        .into(this)
}
