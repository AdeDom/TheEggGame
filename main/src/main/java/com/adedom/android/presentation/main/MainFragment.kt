package com.adedom.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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

        btSingle.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_singleFragment)
        }

        ivRank.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rankFragment)
        }

        ivAbout.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_aboutDialog)
        }

        ivSetting.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingDialog)
        }
    }

}
