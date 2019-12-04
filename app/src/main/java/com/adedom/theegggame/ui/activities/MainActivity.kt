package com.adedom.theegggame.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.ui.dialogs.AboutDialog
import com.adedom.theegggame.ui.dialogs.MissionDialog
import com.adedom.theegggame.ui.dialogs.RankDialog
import com.adedom.theegggame.ui.dialogs.SettingDialog
import com.adedom.theegggame.ui.factories.MainActivityFactory
import com.adedom.theegggame.ui.multi.RoomActivity
import com.adedom.theegggame.ui.single.SingleActivity
import com.adedom.theegggame.ui.viewmodels.MainActivityViewModel
import com.adedom.theegggame.util.BaseActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() { // 2/12/19

    val TAG = "MyTag"
    private lateinit var mViewModel: MainActivityViewModel

    companion object {
        lateinit var sPlayerItem: Player
        val sTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainActivityFactory(PlayerRepository(PlayerApi()))
        mViewModel = ViewModelProviders.of(this,factory).get(MainActivityViewModel::class.java)

        if (checkLogin()) return

        setEvents()
    }

    private fun checkLogin(): Boolean {
        val playerId = this.getPrefLogin(PLAYER_ID)
        if (playerId == "empty") {
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
                    BaseActivity.sContext.toast(R.string.welcome)
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
}
