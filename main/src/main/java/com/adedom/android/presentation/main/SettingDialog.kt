package com.adedom.android.presentation.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseDialogFragment
import com.adedom.teg.presentation.main.MainViewModel
import kotlinx.android.synthetic.main.dialog_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingDialog : BaseDialogFragment(R.layout.dialog_setting) {

    private val viewModel by viewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observeError()

        btHide.setOnClickListener {
            findNavController().popBackStack()
        }

        btChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingDialog_to_changeProfileFragment)
        }

        btChangeImageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingDialog_to_changeImageFragment)
        }

        btChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingDialog_to_changePasswordFragment)
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
        dialog?.dismiss()
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
