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
import com.adedom.theegggame.model.PlayerBean
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.single.SingleActivity
import com.adedom.theegggame.utility.MyMediaPlayer
import com.adedom.utility.MyLibrary
import com.adedom.utility.Setting
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mPlayerId: String
    private var mCountExit = 0

    companion object {
        const val PREF_LOGIN = "PREF_LOGIN"
        lateinit var sActivity: Activity
        lateinit var sContext: Context
        lateinit var sPlayerItem: PlayerBean
        val sTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sActivity = this@MainActivity
        sContext = baseContext

        Setting(this, this)

        if (checkLogin()) return

        setEvents()
    }

    private fun checkLogin(): Boolean {
        mPlayerId = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE)
            .getString("player_id", "empty")!!
        if (mPlayerId == "empty") {
            startActivity(
                Intent(baseContext, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finishAffinity()
            return true
        } else {
//            Pathiphon.call("sp_get_player_by_player_id")
//                .parameter(mPlayerId)
//                .commitQuery {
//                    if (it.next()) {
//                        val player = PlayerBean(
//                            it.getString(1),
//                            it.getString(2),
//                            it.getString(3),
//                            it.getString(4),
//                            it.getInt(5),
//                            it.getString(6)
//                        )
//                        sPlayerItem = player
//
//                        MyLibrary.with(baseContext).showShort(R.string.welcome)
//                        setWidgets()
//                    } else {
//                        getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit()
//                            .putString("player_id", "empty")
//                            .apply()
//                        startActivity(
//                            Intent(baseContext, LoginActivity::class.java)
//                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        )
//                        finishAffinity()
//                    }
//                }
        }
        return false
    }

    private fun setWidgets() {
        if (sPlayerItem.image != "empty") {
            MyLibrary.with(baseContext).glideProfile(sPlayerItem.image, mImgProfile)
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
        if (mCountExit > 0) {
            finishAffinity()
        }
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        MyLibrary.with(baseContext).showShort(R.string.on_back_pressed)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)
        MyMediaPlayer.getMusic(baseContext, R.raw.music_main)
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)
        MyMediaPlayer.music!!.stop()
    }
}
