package com.adedom.theegggame

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.adedom.theegggame.dialog.AboutDialog
import com.adedom.theegggame.dialog.MissionDialog
import com.adedom.theegggame.dialog.RankDialog
import com.adedom.theegggame.dialog.SettingDialog
import com.adedom.theegggame.model.PlayerItem
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.single.SingleActivity
import com.adedom.theegggame.utility.MyGlide
import com.adedom.theegggame.utility.MyIntent
import com.adedom.theegggame.utility.MyMediaPlayer
import com.adedom.theegggame.utility.MyToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() { // 14/7/62

    val TAG = "MainActivity"
    private var mCountExit = 0

    companion object {
        lateinit var mPlayerItem: PlayerItem
        lateinit var mActivity: Activity
        val mTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mActivity = this@MainActivity
        mPlayerItem = intent.getParcelableExtra("values1")

        setWidgets()
        setEvents()
    }

    private fun setWidgets() {
        if (mPlayerItem.image.isNotEmpty()) {
            MyGlide.getGlide(baseContext, mImgProfile, mPlayerItem.image)
        }

        mTvName.text = mPlayerItem.name
        mTvLevel.text = "Level : ${mPlayerItem.level}"
    }

    private fun setEvents() {
        mBtnSingle.setOnClickListener { MyIntent().getIntent(baseContext, SingleActivity::class.java) }
        mBtnMulti.setOnClickListener { MyIntent().getIntent(baseContext, RoomActivity::class.java) }
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
        MyToast.showShort(baseContext, "Press back again to exit")
    }

    override fun onResume() {
        super.onResume()
        MyMediaPlayer.getMusic(baseContext, R.raw.music_main)
    }

    override fun onPause() {
        super.onPause()
        MyMediaPlayer.mMusic!!.stop()
    }
}
