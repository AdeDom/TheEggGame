package com.adedom.android.presentation.main

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setImageCircle
import com.adedom.teg.presentation.main.MainViewModel
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

            ivImageProfile.setImageCircle(playerInfo.image)
            tvName.text = playerInfo.name
            tvLevel.text = getString(R.string.level, playerInfo.level)
        })

        viewModel.error.observeError()

        viewModel.fetchPlayerInfo()

//        btSignOut.setOnClickListener {
//            viewModel.signOut()
//            findNavController().navigate(R.id.action_global_splashScreenFragment)
//        }
//
//        btChangePassword.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_changePasswordFragment)
//        }
//
//        btChangeProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_changeProfileFragment)
//        }
//
//        btChangeImageProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_changeImageFragment)
//        }
    }

}
