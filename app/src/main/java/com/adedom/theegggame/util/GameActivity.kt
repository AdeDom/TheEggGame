package com.adedom.theegggame.util

import android.os.Handler

abstract class GameActivity : BaseActivity() {

    private val mHandlerRefresh = Handler()
    var gameLoop: (() -> Unit)?=null

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
            gameLoop?.invoke()
            mHandlerRefresh.postDelayed(this, 5000)
        }
    }

}