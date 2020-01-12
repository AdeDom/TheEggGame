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
import com.adedom.utility.extension.exitApplication
import com.adedom.utility.extension.getPrefLogin
import com.adedom.utility.extension.login
import com.adedom.utility.extension.toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : GameActivity<MainActivityViewModel>() {

    companion object {
        lateinit var sPlayer: Player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        timeStamp = System.currentTimeMillis() / 1000
        rndkey = UUID.randomUUID().toString().replace("-", "")

        init()

        if (checkPlayer()) return
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
        val date = getDateTime(DATE)
        val time = getDateTime(TIME)
        val playerId = this.getPrefLogin(PLAYER_ID)
        viewModel.insertLogs(rndkey, date, time, playerId).observe(this, Observer {
            if (it.result == COMPLETED) baseContext.toast(R.string.welcome)
        })
    }

    override fun gameLoop() {
        viewModel.getPlayer(playerId!!).observe(this, Observer {
            if (it.playerId == null) {
                this.login(LoginActivity::class.java)
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
