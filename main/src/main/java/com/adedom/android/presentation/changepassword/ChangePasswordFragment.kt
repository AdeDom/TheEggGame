package com.adedom.android.presentation.changepassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.changepassword.ChangePasswordViewModel
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {

    private val viewModel by viewModel<ChangePasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.changePasswordEvent.observe { response ->
            layoutRoot.snackbar(response.message)
            if (response.success) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
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
