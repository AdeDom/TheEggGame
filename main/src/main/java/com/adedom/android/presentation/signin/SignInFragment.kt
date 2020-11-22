package com.adedom.android.presentation.signin

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.hideSoftKeyboard
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.presentation.signin.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        // observe
        viewModel.state.observe { state ->
            btSignIn.isEnabled = state.isValidUsername && state.isValidPassword
            btSignIn.isClickable = state.isClickable

            animationViewLoading.setVisibility(state.loading)
        }

        viewModel.signInEvent.observe {
            when (it) {
                ValidateSignIn.VALIDATE_ERROR -> rootLayout.snackbar(it.toString())
                ValidateSignIn.USERNAME_EMPTY -> rootLayout.snackbar(it.toString())
                ValidateSignIn.USERNAME_INCORRECT -> rootLayout.snackbar(it.toString())
                ValidateSignIn.PASSWORD_EMPTY -> rootLayout.snackbar(it.toString())
                ValidateSignIn.PASSWORD_INCORRECT -> rootLayout.snackbar(it.toString())
                else -> viewModel.callSignIn()
            }
        }

        viewModel.signIn.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
            } else {
                rootLayout.snackbar(response.message)
            }
        }

        viewModel.attachFirstTime.observe {
            val username = viewModel.getConfigUsername()
            viewModel.setStateUsername(username)
            etUsername.setText(username)
        }

        viewModel.error.observeError()

        // set state
        etUsername.addTextChangedListener { viewModel.setStateUsername(it.toString()) }
        etPassword.addTextChangedListener { viewModel.setStatePassword(it.toString()) }

        // event
        btSignIn.setOnClickListener { viewModel.onSignIn() }
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) viewModel.onSignIn()
            false
        }

        btSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        rootLayout.setOnClickListener { activity?.hideSoftKeyboard() }
    }

}
