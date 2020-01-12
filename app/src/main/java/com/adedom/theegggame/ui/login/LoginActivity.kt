package com.adedom.theegggame.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.USERNAME
import com.adedom.utility.extension.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : GameActivity<LoginActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        init()
    }

    private fun init() {
        mEtUsername.setText(this.getPrefLogin(USERNAME))

        mBtRegister.setOnClickListener {
            RegisterDialog().show(supportFragmentManager, null)
        }
        mBtLogin.setOnClickListener { login() }
        mTvForgotPassword.setOnClickListener { baseContext.failed() }
    }

    private fun login() {
        // TODO: 20/05/2562 login one user only

        when {
            mEtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        val username = mEtUsername.getContent()
        val password = mEtPassword.getContent()

        viewModel.getPlayerId(username, password).observe(this, Observer {
            if (it.result == null) {
                mEtPassword.failed(getString(R.string.username_password_incorrect))
            } else {
                this.login(MainActivity::class.java, it.result!!, username)
            }
        })
    }

}
