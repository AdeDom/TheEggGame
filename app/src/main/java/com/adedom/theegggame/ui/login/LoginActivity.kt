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
        val username = this.getPrefLogin(USERNAME)
        mEdtUsername.setText(username)

        mBtnReg.setOnClickListener {
            RegisterPlayerDialog()
                .show(supportFragmentManager, null)
        }
        mBtnLogin.setOnClickListener { loginToMain() }
        mTvForgotPassword.setOnClickListener { baseContext.failed() }
    }

    private fun loginToMain() {
        // TODO: 20/05/2562 login one user only

        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        val username = mEdtUsername.getContent()
        val password = mEdtPassword.getContent()

        viewModel.getPlayerIdLogin(username, password).observe(this, Observer {
            if (it.result == null) {
                mEdtPassword.failed(getString(R.string.username_password_incorrect))
            } else {
                this.login(MainActivity::class.java, it.result!!, username)
            }
        })
    }

}
