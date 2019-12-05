package com.adedom.theegggame.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adedom.utility.Setting

abstract class BaseActivity : AppCompatActivity() {

    val TAG = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sActivity = this@BaseActivity
        sContext = baseContext

        Setting(sActivity, sContext)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)

        // todo online
        // todo music
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)

        // offline
        // music
    }

    companion object {
        lateinit var sActivity: Activity
        lateinit var sContext: Context
    }
}