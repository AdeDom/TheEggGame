package com.adedom.theegggame.util

import android.os.Handler
import com.adedom.theegggame.R
import com.adedom.utility.toast

abstract class BasicActivity : BaseActivity() {

    private var mCountExit = 0

    override fun onBackPressed() {
        if (mCountExit > 0) finishAffinity()
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        sContext.toast(R.string.on_back_pressed)
    }
}