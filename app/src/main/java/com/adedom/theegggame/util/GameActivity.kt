package com.adedom.theegggame.util

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

abstract class GameActivity: AppCompatActivity() {

    private val mHandlerRefresh = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sContext = baseContext
    }

    override fun onResume() {
        super.onResume()
        mRunnableRefresh.run()
    }

    override fun onPause() {
        super.onPause()
        mHandlerRefresh.removeCallbacks(mRunnableRefresh)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandlerRefresh.removeCallbacks(mRunnableRefresh)

    }

    private val mRunnableRefresh = object : Runnable {
        override fun run() {
            mHandlerRefresh.postDelayed(this, 5000)
        }
    }

    companion object{
        lateinit var sContext: Context
    }

}