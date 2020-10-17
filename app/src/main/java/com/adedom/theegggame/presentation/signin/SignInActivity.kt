package com.adedom.theegggame.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.presentation.signin.SignInViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.presentation.signup.SignUpActivity
import com.adedom.theegggame.presentation.splashscreen.SplashScreenActivity
import com.adedom.theegggame.util.extension.snackbar
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {

    private val viewModel by viewModel<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // observe
        viewModel.state.observe { state ->
            btSignIn.isEnabled = state.isValidUsername && state.isValidPassword
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.signInEvent.observe {
            when (it) {
                ValidateSignIn.VALIDATE_ERROR -> layoutRoot.snackbar(it.toString())
                ValidateSignIn.USERNAME_EMPTY -> layoutRoot.snackbar(it.toString())
                ValidateSignIn.USERNAME_INCORRECT -> layoutRoot.snackbar(it.toString())
                ValidateSignIn.PASSWORD_EMPTY -> layoutRoot.snackbar(it.toString())
                ValidateSignIn.PASSWORD_INCORRECT -> layoutRoot.snackbar(it.toString())
                else -> viewModel.callSignIn()
            }
        }

        viewModel.signIn.observe { response ->
            Toast.makeText(baseContext, response.message, Toast.LENGTH_SHORT).show()
            if (response.success) {
                Intent(baseContext, SplashScreenActivity::class.java).apply {
                    finish()
                    startActivity(this)
                }
            }
        }

        viewModel.error.observeError()

        // set state
        etUsername.addTextChangedListener { viewModel.setUsername(it.toString()) }
        etPassword.addTextChangedListener { viewModel.setPassword(it.toString()) }

        // event
        btSignIn.setOnClickListener { viewModel.onSignIn() }
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) viewModel.onSignIn()
            false
        }

        btSignUp.setOnClickListener {
            Intent(baseContext, SignUpActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

}
