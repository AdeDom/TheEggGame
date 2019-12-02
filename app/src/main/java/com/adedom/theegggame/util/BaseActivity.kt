package com.adedom.theegggame.util

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.activities.MainActivity
import com.adedom.utility.Setting
import com.adedom.utility.toast

abstract class BaseActivity : AppCompatActivity() {

    private var mCountExit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Setting(this, this)
    }

    override fun onBackPressed() {
        if (mCountExit > 0) finishAffinity()
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        MainActivity.sContext.toast(R.string.on_back_pressed)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)

        // online
        // music
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)

        // offline
        // music
    }
}