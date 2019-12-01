package com.adedom.theegggame

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.dialog.RegisterDialog
import com.adedom.theegggame.viewmodels.LoginActivityViewModel
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val TAG = "MyTag"
    private lateinit var mViewModel: LoginActivityViewModel

    companion object {
        lateinit var sContext: Context

//        fun loginToMain(activity: Activity, context: Context, playerId: String, username: String) {
//            context.getSharedPreferences(MainActivity.PREF_LOGIN, Context.MODE_PRIVATE).edit()
//                .putString("player_id", playerId)
//                .putString("username", username)
//                .apply()
//            activity.finishAffinity()
//            activity.startActivity(Intent(context, MainActivity::class.java))
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sContext = baseContext

        mViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        Setting(this, this)

        setWidgets()
        setEvents()
    }

    private fun setWidgets() {
        val username = this.getPrefLogin(USERNAME)
        mEdtUsername.setText(username)
    }

    private fun setEvents() {
        mBtnReg.setOnClickListener { RegisterDialog().show(supportFragmentManager, null) }
        mBtnLogin.setOnClickListener { loginToMain() }
        mTvForgotPassword.setOnClickListener { sContext.failed() }
    }

    private fun loginToMain() {
        // TODO: 20/05/2562 login one user only

        val username = mEdtUsername.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()

        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        mViewModel.repository.getPlayerId(username, password)
        mViewModel.player.observe(this, Observer {
            if (it.playerId == null) {
                mEdtPassword.text.clear()
                sContext.toast(R.string.username_password_incorrect, Toast.LENGTH_LONG)
            } else {
                this.login(MainActivity::class.java, it.playerId, username)
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
