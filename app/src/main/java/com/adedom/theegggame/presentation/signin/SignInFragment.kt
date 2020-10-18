package com.adedom.theegggame.presentation.signin

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.presentation.signin.SignInViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseFragment
import com.adedom.theegggame.util.extension.snackbar
import com.adedom.theegggame.util.extension.toast
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
            } else {
                context?.toast(response.message)
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
//            Intent(baseContext, SignUpActivity::class.java).apply {
//                startActivity(this)
//            }
        }
    }

}
