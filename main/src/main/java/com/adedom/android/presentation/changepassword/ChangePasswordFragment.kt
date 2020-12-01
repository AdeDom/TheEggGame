package com.adedom.android.presentation.changepassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.changepassword.ChangePasswordViewModel
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {

    private val viewModel by viewModel<ChangePasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            btChangePassword.isClickable = state.isClickable
        }

        viewModel.changePasswordEvent.observe { response ->
            requireView().snackbar(response.message)
            if (response.success) {
                activity?.finish()
                findNavController().navigate(R.id.action_global_authActivity)
            }
        }

        etOldPassword.addTextChangedListener { viewModel.setStateOldPassword(it.toString()) }
        etNewPassword.addTextChangedListener { viewModel.setStateNewPassword(it.toString()) }
        etReNewPassword.addTextChangedListener { viewModel.setStateReNewPassword(it.toString()) }

        btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btChangePassword.setOnClickListener {
            viewModel.callChangePassword()
        }
    }

}
