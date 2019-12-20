package com.adedom.theegggame.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.ui.multi.room.RoomActivity
import com.adedom.theegggame.ui.single.SingleActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.adedom.utility.extension.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : GameActivity<MainActivityViewModel>() {

    companion object {
        lateinit var sPlayerItem: Player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        timeStamp = System.currentTimeMillis() / 1000
        randomKey = UUID.randomUUID().toString().replace("-", "")

        init()

        if (checkPlayer()) return
    }

    private fun init() {
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

    private fun checkPlayer(): Boolean {
        if (playerId == EMPTY) {
            this.login(
                LoginActivity::class.java,
                username = this.getPrefLogin(USERNAME)
            )
            return true
        } else insertLogs()
        return false
    }

    private fun insertLogs() {
        val playerId = this.getPrefLogin(PLAYER_ID)
        viewModel.insertLogs(randomKey, getDateTime(DATE), getDateTime(TIME), playerId)
            .observe(this, Observer {
                if (it.result == COMPLETED) baseContext.toast(R.string.welcome)
            })
    }

    override fun gameLoop() {
        viewModel.getPlayers(playerId!!).observe(this, Observer {
            if (it.playerId == null) {
                this.login(LoginActivity::class.java)
            } else {
                sPlayerItem = it
                if (sPlayerItem.image != EMPTY) mImgProfile.loadProfile(sPlayerItem.image!!)
                mTvName.text = sPlayerItem.name
                mTvLevel.text = getLevel(sPlayerItem.level)
            }
        })
    }

    override fun onBackPressed() = this.exitApplication()
}
