package com.adedom.android.presentation.endteggame

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.teg.presentation.endteggame.EndTegGameViewModel
import kotlinx.android.synthetic.main.fragment_end_teg_game.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EndTegGameFragment : BaseFragment(R.layout.fragment_end_teg_game) {

    private val viewModel by viewModel<EndTegGameViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        viewEvent()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchMultiPlayerEndTeg()
        }

        viewModel.state.observe { state ->
            animationViewLoading.isVisible = state.isLoading

            tvTeamA.text = state.resultTeamA?.capitalize(Locale.getDefault())
            tvTeamB.text = state.resultTeamB?.capitalize(Locale.getDefault())

            tvScoreTeamA.text = state.scoreTeamA.toString()
            tvScoreTeamB.text = state.scoreTeamB.toString()

            btEndGame.isVisible = !state.isBonusEndGame
            btBonusEndGame.isVisible = state.isBonusEndGame
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        btEndGame.setOnClickListener {
            findNavController().popBackStack()
        }

        btBonusEndGame.setOnClickListener {
            findNavController().navigate(R.id.action_endTegGameFragment_to_bonusTegGameFragment)
        }
    }

}
