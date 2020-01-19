package com.adedom.theegggame.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.exitApplication
import com.adedom.library.extension.getPrefFile
import com.adedom.library.extension.toast
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.KEY_EMPTY
import com.adedom.library.util.KEY_TIME
import com.adedom.library.util.getDateTime
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.ui.multi.room.RoomActivity
import com.adedom.theegggame.ui.single.SingleActivity
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.loginSuccess
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GameActivity<MainActivityViewModel>() {

    companion object {
        lateinit var sPlayer: Player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        init()

        if (verifyPlayer()) return
    }

    private fun init() {
        mBtSingle.setOnClickListener {
            startActivity(Intent(baseContext, SingleActivity::class.java))
        }
        mBtMulti.setOnClickListener {
            startActivity(Intent(baseContext, RoomActivity::class.java))
        }
        mIvMission.setOnClickListener {
            MissionDialog().show(supportFragmentManager, null)
        }
        mIvRank.setOnClickListener {
            RankDialog().show(supportFragmentManager, null)
        }
        mIvAbout.setOnClickListener {
            AboutDialog().show(supportFragmentManager, null)
        }
        mIvSetting.setOnClickListener {
            SettingDialog().show(supportFragmentManager, null)
        }
    }

    private fun verifyPlayer(): Boolean {
        return if (this.getPrefFile(KEY_PLAYER_ID) != KEY_EMPTY && this.getPrefFile(KEY_USERNAME) != "") {
            insertLogs()
            false
        } else {
            this.loginSuccess(
                LoginActivity::class.java,
                KEY_EMPTY,
                this.getPrefFile(KEY_USERNAME)
            )
            true
        }
    }

    private fun insertLogs() {
        val date = getDateTime(KEY_DATE)
        val time = getDateTime(KEY_TIME)
        val playerId = this.getPrefFile(KEY_PLAYER_ID)
        viewModel.insertLogs(MainActivityViewModel.rndkey, date, time, playerId)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) baseContext.toast(R.string.welcome)
            })
    }

    override fun gameLoop() {
        viewModel.getPlayer(playerId!!).observe(this, Observer {
            if (it.playerId == null) {
                this.loginSuccess(LoginActivity::class.java, KEY_EMPTY, "")
            } else {
                sPlayer = it
                setImageProfile(mIvProfile, sPlayer.image!!, sPlayer.gender!!)
                mTvName.text = sPlayer.name
                mTvLevel.text = getLevel(sPlayer.level)
            }
        })
    }

    override fun onBackPressed() = this.exitApplication()
}
