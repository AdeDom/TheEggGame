package com.adedom.theegggame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adedom.theegggame.dialog.InsertPlayerDialog
import com.adedom.utility.MyLibrary
import com.adedom.utility.Pathiphon
import com.adedom.utility.Setting
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() { // 10/11//19

    companion object {
        lateinit var sContext: Context

        fun login(activity: Activity, context: Context, playerId: String, username: String) {
            context.getSharedPreferences(MainActivity.PREF_LOGIN, Context.MODE_PRIVATE).edit()
                .putString("player_id", playerId)
                .putString("username", username.trim())
                .apply()
            activity.finishAffinity()
            activity.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sContext = baseContext

        Setting(this, this)

        setWidgets()
        setEvents()
    }

    private fun setWidgets() {
        val preferences = getSharedPreferences(MainActivity.PREF_LOGIN, Context.MODE_PRIVATE)
        val username = preferences.getString("username", "")!!
        mEdtUsername.setText(username)
    }

    private fun setEvents() {
        mBtnReg.setOnClickListener { InsertPlayerDialog().show(supportFragmentManager, null) }
        mBtnLogin.setOnClickListener { login() }
        mTvForgotPassword.setOnClickListener { MyLibrary.failed(baseContext) }
    }

    private fun login() {
        // TODO: 20/05/2562 login one user only

        val username = mEdtUsername.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()

        when {
            MyLibrary.isEmpty(mEdtUsername, getString(R.string.error_username)) -> return
            MyLibrary.isEmpty(mEdtPassword, getString(R.string.error_password)) -> return
        }

        Ion.with(baseContext)
            .load(Pathiphon.BASE_URL + "2-1.php")
            .setBodyParameter("spName", "sp_login_player")
            .setBodyParameter("values1", username)
            .setBodyParameter("values2", password)
            .asJsonArray()
            .setCallback { e, result ->
                if (result.size() == 0) {
                    mEdtPassword.text.clear()
                    MyLibrary.with(baseContext).showLong(R.string.username_password_incorrect)
                } else {
                    for (i in 0 until result.size()) {
                        val jsObject = result.get(i) as JsonObject
                        val values = jsObject.get("values1").asString.trim()
                        login(this, baseContext, values, username)
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)
//        MyMediaPlayer.getMusic(baseContext, R.raw.music_main)
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)
//        MyMediaPlayer.music!!.stop()
    }
}
