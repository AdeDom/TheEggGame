package com.adedom.android.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.toast
import com.adedom.teg.presentation.signup.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.signUpEvent.observe { response ->
            context?.toast(response.message)
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
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
            findNavController().popBackStack()
        }
    }

}
