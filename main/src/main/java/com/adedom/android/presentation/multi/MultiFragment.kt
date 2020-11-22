package com.adedom.android.presentation.multi

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.multi.MultiViewModel
import com.adedom.teg.presentation.multi.TegMultiPlayerListener
import kotlinx.android.synthetic.main.fragment_multi.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MultiFragment : BaseFragment(R.layout.fragment_multi), TegMultiPlayerListener {

    private val viewModel by viewModel<MultiViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listener = this

        viewModel.attachFirstTime.observe {
            viewModel.callTimerTegMultiPlayer()
        }

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)
        }
    }

    override fun onTimerTegMultiPlayer(timer: String) {
        tvTimer.text = timer
    }

    override fun onEndTegMultiPlayer() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Title")
            setMessage("Message")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

}
