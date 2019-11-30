package com.adedom.theegggame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.adedom.theegggame.dialog.AboutDialog
import com.adedom.theegggame.dialog.MissionDialog
import com.adedom.theegggame.dialog.RankDialog
import com.adedom.theegggame.dialog.SettingDialog
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.single.SingleActivity
import com.adedom.utility.MyLibrary
import com.adedom.utility.Pathiphon
import com.adedom.utility.Setting
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCountExit = 0

    companion object {
        const val PREF_LOGIN = "PREF_LOGIN"
        lateinit var sActivity: Activity
        lateinit var sContext: Context
        lateinit var sPlayerItem: Player
//        val sTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sActivity = this@MainActivity
        sContext = baseContext

        if (checkLogin()) return

        Setting(sActivity, sContext)

        setEvents()
    }

    private fun checkLogin(): Boolean {
        val playerId = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE)
            .getString("player_id", "empty")!!
        if (playerId == "empty") {
            startActivity(
                Intent(baseContext, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finishAffinity()
            return true
        } else {
            Ion.with(baseContext)
                .load(Pathiphon.BASE_URL + "get-player.php")
                .setBodyParameter("values1", playerId)
                .asJsonArray()
                .setCallback { e, result ->
                    if (result.size() == 0) {
                        getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit()
                            .putString("player_id", "empty")
                            .apply()
                        startActivity(
                            Intent(baseContext, LoginActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        finishAffinity()
                    } else {
                        for (i in 0 until result.size()) {
                            val jsObject = result.get(i) as JsonObject
                            val item = Player(
                                jsObject.get("values1").asString.trim(),
                                jsObject.get("values2").asString.trim(),
                                jsObject.get("values3").asString.trim(),
                                jsObject.get("values4").asString.trim(),
                                jsObject.get("values5").asInt,
                                jsObject.get("values6").asString.trim()
                            )
                            sPlayerItem = item
                        }
                        MyLibrary.with(baseContext).showShort(R.string.welcome)
                        setWidgets()
                    }
                }
        }
        return false
    }

    private fun setWidgets() {
        if (sPlayerItem.image != "empty") {
            MyLibrary.with(baseContext).glideProfile(sPlayerItem.image!!, mImgProfile)
        }

        mTvName.text = sPlayerItem.name
        mTvLevel.text = "Level : ${sPlayerItem.level}"
    }

    private fun setEvents() {
        mBtnSingle.setOnClickListener {
            startActivity(Intent(baseContext, SingleActivity::class.java))
        }
        mBtnMulti.setOnClickListener {
            startActivity(Intent(baseContext, RoomActivity::class.java))
        }
        mImgMission.setOnClickListener { MissionDialog().show(supportFragmentManager, null) }
        mImgRank.setOnClickListener { RankDialog().show(supportFragmentManager, null) }
        mImgAbout.setOnClickListener { AboutDialog().show(supportFragmentManager, null) }
        mImgSetting.setOnClickListener { SettingDialog().show(supportFragmentManager, null) }
    }

    override fun onBackPressed() {
        if (mCountExit > 0) finishAffinity()
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        MyLibrary.with(baseContext).showShort(R.string.on_back_pressed)
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
