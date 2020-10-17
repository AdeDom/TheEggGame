package com.adedom.theegggame.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adedom.library.extension.exitApplication
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.getDateTime
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.util.TegConstant
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.presentation.changeimage.ChangeImageActivity
import com.adedom.theegggame.presentation.changepassword.ChangePasswordActivity
import com.adedom.theegggame.presentation.changeprofile.ChangeProfileActivity
import com.adedom.theegggame.presentation.splashscreen.SplashScreenActivity
import com.adedom.theegggame.ui.main.AboutDialog
import com.adedom.theegggame.ui.main.MissionDialog
import com.adedom.theegggame.ui.main.RankDialog
import com.adedom.theegggame.ui.main.SettingDialog
import com.adedom.theegggame.ui.multi.room.RoomActivity
import com.adedom.theegggame.ui.single.SingleActivity
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playSoundClick
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: 17/10/2563 insert logs
class MainActivity : BaseActivity() {

    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.observeForever { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.playerInfo.observe(this, { playerInfo ->
            if (playerInfo == null) return@observe

            if (playerInfo.image.isNullOrBlank() && playerInfo.gender.isNullOrBlank())
                setImageProfile(mIvProfile, playerInfo.image!!, playerInfo.gender!!)
            mTvName.text = playerInfo.name
            mTvLevel.text = getString(R.string.level, playerInfo.level)
        })

        viewModel.error.observeError()

        viewModel.fetchPlayerInfo()

        btSignOut.setOnClickListener {
            viewModel.signOut()
            Intent(baseContext, SplashScreenActivity::class.java).apply {
                finishAffinity()
                startActivity(this)
            }
        }

        btChangePassword.setOnClickListener {
            Intent(baseContext, ChangePasswordActivity::class.java).apply {
                startActivity(this)
            }
        }

        btChangeProfile.setOnClickListener {
            Intent(baseContext, ChangeProfileActivity::class.java).apply {
                startActivity(this)
            }
        }

        btChangeImageProfile.setOnClickListener {
            Intent(baseContext, ChangeImageActivity::class.java).apply {
                startActivity(this)
            }
        }

        mBtSingle.setOnClickListener {
            startActivity(Intent(baseContext, SingleActivity::class.java))
            baseContext.playSoundClick()
        }
        mBtMulti.setOnClickListener {
            startActivity(Intent(baseContext, RoomActivity::class.java))
            baseContext.playSoundClick()
        }
        mIvMission.setOnClickListener {
            MissionDialog().show(supportFragmentManager, null)
            baseContext.playSoundClick()
        }
        mIvRank.setOnClickListener {
            RankDialog().show(supportFragmentManager, null)
            baseContext.playSoundClick()
        }
        mIvAbout.setOnClickListener {
            AboutDialog().show(supportFragmentManager, null)
            baseContext.playSoundClick()
        }
        mIvSetting.setOnClickListener {
            SettingDialog().show(supportFragmentManager, null)
            baseContext.playSoundClick()
        }

        writeFile()
    }

    override fun onResume() {
        super.onResume()
        viewModel.callPlayerState(TegConstant.STATE_ONLINE)
    }

    override fun onPause() {
        super.onPause()
        viewModel.callPlayerState(TegConstant.STATE_OFFLINE)
    }

    override fun onBackPressed() = this.exitApplication()

    fun writeFile() {
        if (readPrefFile(SOUND_MUSIC) == KEY_STRING)
            writePrefFile(SOUND_MUSIC, SOUND_MUSIC_ON)

        if (readPrefFile(KEY_MISSION_DATE) == KEY_STRING)
            writePrefFile(KEY_MISSION_DATE, getDateTime(KEY_DATE))

        if (readPrefFile(KEY_MISSION_DELIVERY) == KEY_STRING)
            writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_UNSUCCESSFUL)

        if (readPrefFile(KEY_MISSION_SINGLE) == KEY_STRING)
            writePrefFile(KEY_MISSION_SINGLE, KEY_MISSION_UNSUCCESSFUL)

        if (readPrefFile(KEY_MISSION_SINGLE_GAME) == KEY_STRING)
            writePrefFile(KEY_MISSION_SINGLE_GAME, KEY_MISSION_UNSUCCESSFUL)

        if (readPrefFile(KEY_MISSION_MULTI) == KEY_STRING)
            writePrefFile(KEY_MISSION_MULTI, KEY_MISSION_UNSUCCESSFUL)

        if (readPrefFile(KEY_MISSION_MULTI_GAME) == KEY_STRING)
            writePrefFile(KEY_MISSION_MULTI_GAME, KEY_MISSION_UNSUCCESSFUL)

        //delivery
        if (readPrefFile(KEY_MISSION_DATE) != getDateTime(KEY_DATE)) {
            writePrefFile(KEY_MISSION_DATE, getDateTime(KEY_DATE))
            writePrefFile(KEY_MISSION_DELIVERY, KEY_MISSION_UNSUCCESSFUL)
            writePrefFile(KEY_MISSION_SINGLE, KEY_MISSION_UNSUCCESSFUL)
            writePrefFile(KEY_MISSION_SINGLE_GAME, KEY_MISSION_UNSUCCESSFUL)
            writePrefFile(KEY_MISSION_MULTI, KEY_MISSION_UNSUCCESSFUL)
            writePrefFile(KEY_MISSION_MULTI_GAME, KEY_MISSION_UNSUCCESSFUL)
        }

    }

}
