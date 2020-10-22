package com.adedom.android.presentation.setting

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.teg.presentation.setting.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    private val viewModel by viewModel<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observeError()

        btChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_changeProfileFragment)
        }

        btChangeImageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_changeImageFragment)
        }

        btChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_changePasswordFragment)
        }

        // TODO: 20/10/2563 sound & music

        btSignOut.setOnClickListener {
            viewModel.signOut()
            findNavController().navigate(R.id.action_global_splashScreenFragment)
        }

        btExit.setOnClickListener {
            exitApp()
        }
    }

    private fun exitApp() {
        AlertDialog.Builder(activity).apply {
            setTitle(R.string.exit)
            setMessage(R.string.exit_message)
            setIcon(R.drawable.ic_exit)
            setPositiveButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.ok) { _, _ ->
                activity?.finishAffinity()
            }
            show()
        }
    }

}
