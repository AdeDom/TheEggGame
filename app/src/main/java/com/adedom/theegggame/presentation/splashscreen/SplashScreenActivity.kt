package com.adedom.theegggame.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import com.adedom.teg.presentation.splashscreen.SplashScreenViewModel
import com.adedom.theegggame.presentation.main.MainActivity
import com.adedom.theegggame.presentation.signin.SignInActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : BaseActivity() {

    val viewModel by viewModel<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel.attachFirstTime.observe {
            if (viewModel.initialize()) {
                Intent(baseContext, MainActivity::class.java).apply {
                    finish()
                    startActivity(this)
                }
            } else {
                Intent(baseContext, SignInActivity::class.java).apply {
                    finish()
                    startActivity(this)
                }
            }
        }

        viewModel.error.observeError()

    }

}
