package com.adedom.theegggame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.dialog.RegisterDialog
import com.adedom.theegggame.factories.LoginActivityFactory
import com.adedom.theegggame.networks.PlayerApi
import com.adedom.theegggame.repositories.PlayerRepository
import com.adedom.theegggame.viewmodels.LoginActivityViewModel
import com.adedom.utility.MyLibrary
import com.adedom.utility.Setting
import com.adedom.utility.isEmpty
import com.adedom.utility.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val TAG = "MyTag"
    private lateinit var mViewModel: LoginActivityViewModel

    companion object {
        lateinit var sContext: Context

        fun login(activity: Activity, context: Context, playerId: String, username: String) {
            context.getSharedPreferences(MainActivity.PREF_LOGIN, Context.MODE_PRIVATE).edit()
                .putString("player_id", playerId)
                .putString("username", username)
                .apply()
            activity.finishAffinity()
            activity.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sContext = baseContext

        val factory = LoginActivityFactory(PlayerRepository(PlayerApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(LoginActivityViewModel::class.java)

        Setting(this, this)

        setWidgets()
        setEvents()
    }

    private fun setWidgets() {
        val preferences = getSharedPreferences(MainActivity.PREF_LOGIN, Context.MODE_PRIVATE)
        val username = preferences.getString("username", "")!!
        mEdtUsername.setText(username)
    }

    private fun setEvents() {
        mBtnReg.setOnClickListener { RegisterDialog().show(supportFragmentManager, null) }
        mBtnLogin.setOnClickListener { login() }
        mTvForgotPassword.setOnClickListener { MyLibrary.failed(baseContext) }
    }

    private fun login() {
        // TODO: 20/05/2562 login one user only

        val username = mEdtUsername.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()

        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        mViewModel.getPlayerId(username, password)
        mViewModel.playerId.observe(this, Observer {
            if (it.playerId == null) {
                mEdtPassword.text.clear()
                sContext.toast(R.string.username_password_incorrect, Toast.LENGTH_LONG)
            } else {
                sContext.toast("OK")
            }
        })

    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)
    }
}
