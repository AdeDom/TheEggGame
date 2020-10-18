package com.adedom.android.presentation.changeprofile

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.changeprofile.ChangeProfileViewModel
import kotlinx.android.synthetic.main.fragment_change_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProfileFragment : BaseFragment(R.layout.fragment_change_profile) {

    private val viewModel by viewModel<ChangeProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.playerInfo.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            etName.setText(playerInfo.name)
            etGender.setText(playerInfo.gender)
            etBirthDate.setText(playerInfo.birthDate)
        })

        viewModel.changeProfileEvent.observe { response ->
            layoutRoot.snackbar(response.message)
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        etName.addTextChangedListener { viewModel.setStateName(it.toString()) }
        etGender.addTextChangedListener { viewModel.setStateGender(it.toString()) }
        etBirthDate.addTextChangedListener { viewModel.setStateBirthDate(it.toString()) }

        btChangeProfile.setOnClickListener {
            viewModel.callChangeProfile()
        }
    }

}
