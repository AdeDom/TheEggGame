package com.adedom.theegggame.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.ui.dialogs.AboutDialog
import com.adedom.theegggame.ui.dialogs.MissionDialog
import com.adedom.theegggame.ui.dialogs.SettingDialog
import com.adedom.theegggame.ui.dialogs.rank.RankDialog
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.ui.multi.room.RoomActivity
import com.adedom.theegggame.ui.single.SingleActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GameActivity() { // 2/12/19

    private lateinit var mViewModel: MainActivityViewModel
    private var mCountExit = 0

    companion object {
        lateinit var sPlayerItem: Player
        val sTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainActivityFactory(
            PlayerRepository(PlayerApi())
        )
        mViewModel = ViewModelProviders.of(this,factory).get(MainActivityViewModel::class.java)

        if (checkLogin()) return

        setEvents()
    }

    private fun checkLogin(): Boolean {
        val playerId = this.getPrefLogin(PLAYER_ID)
        if (playerId == EMPTY) {
            this.login(
                LoginActivity::class.java,
                username = this.getPrefLogin(USERNAME)
            )
            return true
        } else {
            mViewModel.getPlayers(playerId).observe(this, Observer {
                if (it.playerId == null) {
                    this.login(LoginActivity::class.java)
                } else {
                    sPlayerItem = it
                    GameActivity.sContext.toast(R.string.welcome)
                    setWidgets()
                }
            })
        }
        return false
    }

    private fun setWidgets() {
        if (sPlayerItem.image != EMPTY) {
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
        mImgMission.setOnClickListener {
            MissionDialog()
                .show(supportFragmentManager, null)
        }
        mImgRank.setOnClickListener {
            RankDialog()
                .show(supportFragmentManager, null)
        }
        mImgAbout.setOnClickListener {
            AboutDialog()
                .show(supportFragmentManager, null)
        }
        mImgSetting.setOnClickListener {
            SettingDialog()
                .show(supportFragmentManager, null)
        }
    }

    override fun onBackPressed() {
        if (mCountExit > 0) finishAffinity()
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        sContext.toast(R.string.on_back_pressed)
    }
}
