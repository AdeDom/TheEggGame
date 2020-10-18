package com.adedom.theegggame.presentation.changepassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.adedom.teg.presentation.changepassword.ChangePasswordViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.util.extension.toast
import kotlinx.android.synthetic.main.activity_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordActivity : BaseActivity() {

    val viewModel by viewModel<ChangePasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.changePasswordEvent.observe { response ->
            toast(response.message)
            if (response.success) {
//                Intent(baseContext, SplashScreenActivity::class.java).apply {
//                    finishAffinity()
//                    startActivity(this)
//                }
            }
        }

        etOldPassword.addTextChangedListener { viewModel.setStateOldPassword(it.toString()) }
        etNewPassword.addTextChangedListener { viewModel.setStateNewPassword(it.toString()) }
        etReNewPassword.addTextChangedListener { viewModel.setStateReNewPassword(it.toString()) }

        btChangepassword.setOnClickListener {
            viewModel.callChangePassword()
        }

    }

}
