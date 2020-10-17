package com.adedom.theegggame.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.adedom.teg.presentation.signup.SignUpViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.presentation.main.MainActivity
import com.adedom.theegggame.util.extension.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {

    val viewModel by viewModel<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.signUpEvent.observe { response ->
            toast(response.message)
            if (response.success) {
                Intent(baseContext, MainActivity::class.java).apply {
                    finishAffinity()
                    startActivity(this)
                }
            }
        }

        viewModel.error.observeError()

        etUsername.addTextChangedListener { viewModel.setStateUsername(it.toString()) }
        etPassword.addTextChangedListener { viewModel.setStatePassword(it.toString()) }
        etName.addTextChangedListener { viewModel.setStateName(it.toString()) }
        etGender.addTextChangedListener { viewModel.setStateGender(it.toString()) }
        etBirthDate.addTextChangedListener { viewModel.setStateBirthDate(it.toString()) }

        // event
        btSignUp.setOnClickListener {
            viewModel.callSignUp()
        }

        btBack.setOnClickListener {
            finish()
        }

    }

}
