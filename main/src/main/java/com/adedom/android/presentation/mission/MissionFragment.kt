package com.adedom.android.presentation.mission

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.clicks
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.mission.MissionViewEvent
import com.adedom.teg.presentation.mission.MissionViewModel
import kotlinx.android.synthetic.main.fragment_mission.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class MissionFragment : BaseFragment(R.layout.fragment_mission) {

    private val viewModel by viewModel<MissionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callFetchMission()
        }

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            tvMissionDelivery.isClickable = state.isMissionDelivery
            tvMissionSingle.isClickable = state.isMissionSingle
            tvMissionMulti.isClickable = state.isMissionMulti

            ivMissionDelivery.setVisibility(state.isMissionDelivery)
            ivMissionSingle.setVisibility(state.isMissionSingle)
            ivMissionMulti.setVisibility(state.isMissionMulti)

            ivCorrectDelivery.setVisibility(state.isMissionDeliveryCompleted)
            ivCorrectSingle.setVisibility(state.isMissionSingleCompleted)
            ivCorrectMulti.setVisibility(state.isMissionMultiCompleted)
        }

        viewModel.error.observeError()

        viewEventFlow().observe { viewModel.process(it) }

    }

    private fun viewEventFlow(): Flow<MissionViewEvent> {
        return merge(
            tvMissionDelivery.clicks().map { MissionViewEvent.DELIVERY },
            tvMissionSingle.clicks().map { MissionViewEvent.SINGLE },
            tvMissionMulti.clicks().map { MissionViewEvent.MULTI },
        )
    }

}
