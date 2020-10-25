package com.adedom.android.presentation.mission

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.clicks
import com.adedom.teg.presentation.mission.MissionAction
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
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            tvMissionDelivery.isClickable = state.isMissionDelivery
            tvMissionSingle.isClickable = state.isMissionSingle
            tvMissionMulti.isClickable = state.isMissionMulti
        }

        viewModel.error.observeError()

        actionFlow().observe { viewModel.sendAction(it) }

    }

    private fun actionFlow(): Flow<MissionAction> {
        return merge(
            tvMissionDelivery.clicks().map { MissionAction.DELIVERY },
            tvMissionSingle.clicks().map { MissionAction.SINGLE },
            tvMissionMulti.clicks().map { MissionAction.MULTI },
        )
    }

}
