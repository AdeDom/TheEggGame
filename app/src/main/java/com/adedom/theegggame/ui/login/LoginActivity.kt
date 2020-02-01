package com.adedom.theegggame.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.failed
import com.adedom.library.extension.getContent
import com.adedom.library.extension.isEmpty
import com.adedom.library.extension.readPrefFile
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.KEY_USERNAME
import com.adedom.theegggame.util.extension.loginSuccess
import com.adedom.theegggame.util.extension.playSoundClick
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : GameActivity<LoginActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        init()
    }

    private fun init() {
        mEtUsername.setText(this.readPrefFile(KEY_USERNAME))

        mBtRegister.setOnClickListener {
            RegisterDialog().show(supportFragmentManager, null)
            sContext.playSoundClick()
        }

        mBtLogin.setOnClickListener {
            login()
            sContext.playSoundClick()
        }

        mTvForgotPassword.setOnClickListener {
            baseContext.failed()
            sContext.playSoundClick()
        }
    }

    private fun login() {
        when {
            mEtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        val username = mEtUsername.getContent()
        val password = mEtPassword.getContent()

        viewModel.getPlayerId(username, password).observe(this, Observer {
            if (it.result == null) {
                mEtPassword.failed(getString(R.string.login_incorrect))
            } else {
                this.loginSuccess(MainActivity::class.java, it.result!!, username)
            }
        })
    }

}
