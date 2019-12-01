package com.adedom.theegggame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.dialog.AboutDialog
import com.adedom.theegggame.dialog.MissionDialog
import com.adedom.theegggame.dialog.RankDialog
import com.adedom.theegggame.dialog.SettingDialog
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.single.SingleActivity
import com.adedom.theegggame.viewmodels.MainActivityViewModel
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainActivityViewModel
    private var mCountExit = 0

    companion object {
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

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        if (checkLogin()) return

        Setting(sActivity, sContext)

        setEvents()
    }

    private fun checkLogin(): Boolean {
        val playerId = this.getPrefLogin(PLAYER_ID)
        if (playerId == "empty") {
            startActivity(
                Intent(baseContext, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finishAffinity()
            return true
        } else {
            mViewModel.repository.getPlayers(playerId)
            mViewModel.player.observe(this, Observer {
                if (it.playerId == null) {
                    this.login(LoginActivity::class.java)
                } else {
                    sPlayerItem = it
                    sContext.toast(R.string.welcome)
                    setWidgets()
                }
            })
        }
        return false
    }

    private fun setWidgets() {
        if (sPlayerItem.image != "empty") {
            mImgProfile.loadProfile(sPlayerItem.image!!)
        }

        mTvName.text = sPlayerItem.name
        val level = "Level : ${sPlayerItem.level}"
        mTvLevel.text = level
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
        sContext.toast(R.string.on_back_pressed)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)
    }
}
