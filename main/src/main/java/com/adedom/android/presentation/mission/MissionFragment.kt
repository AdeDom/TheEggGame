package com.adedom.android.presentation.mission

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.teg.presentation.mission.MissionViewModel
import kotlinx.android.synthetic.main.fragment_mission.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MissionFragment : BaseFragment(R.layout.fragment_mission) {

    private val viewModel by viewModel<MissionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callFetchMission()
        }

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            tvMissionDelivery.isClickable = state.isMissionDelivery
        }

        viewModel.error.observeError()

        tvMissionDelivery.setOnClickListener {
            viewModel.callMissionDelivery()
        }

        tvMissionSingle.setOnClickListener {
            // TODO: 24/10/2563 mission single
        }

        tvMissionMulti.setOnClickListener {
            // TODO: 24/10/2563 mission multi
        }

    }

}
