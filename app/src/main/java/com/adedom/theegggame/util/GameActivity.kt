package com.adedom.theegggame.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.failed
import com.adedom.library.extension.readPrefFile
import com.adedom.library.util.pauseMusic
import com.adedom.theegggame.util.extension.playMusicGame

abstract class GameActivity<VM : ViewModel> : AppCompatActivity() {

    val TAG = "GameActivity"
    lateinit var viewModel: VM
    private lateinit var mViewModel: GameActivityViewModel
    var playerId: String? = null
    private val mHandlerFetch = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(GameActivityViewModel::class.java)

        sActivity = this@GameActivity
        sContext = baseContext

//        SettingPermissionAndLocation(sActivity, sContext)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
//        SettingPermissionAndLocation.locationListener(sActivity, true)

        sContext.playMusicGame()

        playerId = this.readPrefFile(KEY_PLAYER_ID)
        mViewModel.setState(playerId!!, KEY_ONLINE).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })

        mRunnableFetch.run()
    }

    override fun onPause() {
        super.onPause()
//        SettingPermissionAndLocation.locationListener(sActivity, false)

        pauseMusic()

        mHandlerFetch.removeCallbacks(mRunnableFetch)

        mViewModel.setState(playerId!!, KEY_OFFLINE).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })

        mViewModel.updateLogs(keyLogs).observe(this, Observer {
            if (it.result == KEY_FAILED) baseContext.failed()
        })
    }

    open fun gameLoop() {}

    override fun onBackPressed() {
        mHandlerFetch.removeCallbacks(mRunnableFetch)
        finish()
        super.onBackPressed()
    }

    private val mRunnableFetch = object : Runnable {
        override fun run() {
            gameLoop()
            mHandlerFetch.postDelayed(this, 5000)
        }
    }

    companion object {
        lateinit var sActivity: Activity
        lateinit var sContext: Context
    }
}

class GameActivityViewModel : BaseViewModel() {
    fun setState(playerId: String, state: String) = baseRepository.setState(playerId, state)

    fun updateLogs(key: String) = baseRepository.updateLogs(key)
}
