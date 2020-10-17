package com.adedom.theegggame.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.util.TegConstant
import com.adedom.theegggame.presentation.changeimage.ChangeImageActivity
import com.adedom.theegggame.presentation.changepassword.ChangePasswordActivity
import com.adedom.theegggame.presentation.changeprofile.ChangeProfileActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.presentation.splashscreen.SplashScreenActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.observeForever { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.playerInfo.observe(this, { playerInfo ->
            if (playerInfo == null) return@observe

            val text = "${playerInfo.name} : ${playerInfo.level}"
            materialTextView.text = text
        })

        viewModel.error.observeError()

        viewModel.fetchPlayerInfo()

        btSignOut.setOnClickListener {
            viewModel.signOut()
            Intent(baseContext, SplashScreenActivity::class.java).apply {
                finishAffinity()
                startActivity(this)
            }
        }

        btChangePassword.setOnClickListener {
            Intent(baseContext, ChangePasswordActivity::class.java).apply {
                startActivity(this)
            }
        }

        btChangeProfile.setOnClickListener {
            Intent(baseContext, ChangeProfileActivity::class.java).apply {
                startActivity(this)
            }
        }

        btChangeImageProfile.setOnClickListener {
            Intent(baseContext, ChangeImageActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callPlayerState(TegConstant.STATE_ONLINE)
    }

    override fun onPause() {
        super.onPause()
        viewModel.callPlayerState(TegConstant.STATE_OFFLINE)
    }

}
