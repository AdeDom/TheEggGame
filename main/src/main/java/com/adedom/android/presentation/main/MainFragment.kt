package com.adedom.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.teg.presentation.main.MainViewModel
import com.adedom.teg.util.TegConstant
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel by viewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observeForever { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.playerInfo.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            val text = "${playerInfo.name} : ${playerInfo.level}"
            materialTextView.text = text
        })

        viewModel.error.observeError()

        viewModel.fetchPlayerInfo()

        btSignOut.setOnClickListener {
            viewModel.signOut()
            findNavController().navigate(R.id.action_global_splashScreenFragment)
        }

        btChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_changePasswordFragment)
        }

        btChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_changeProfileFragment)
        }

        btChangeImageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_changeImageFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callPlayerState(TegConstant.STATE_ONLINE)
    }

    override fun onPause() {
        super.onPause()
        viewModel.callPlayerState(TegConstant.STATE_OFFLINE)
    }

}
