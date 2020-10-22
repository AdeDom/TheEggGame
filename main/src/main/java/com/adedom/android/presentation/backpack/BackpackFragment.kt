package com.adedom.android.presentation.backpack

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.teg.presentation.backpack.BackpackViewModel
import kotlinx.android.synthetic.main.fragment_backpack.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BackpackFragment : BaseFragment(R.layout.fragment_backpack) {

    private val viewModel by viewModel<BackpackViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callFetchItemCollection()
        }

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            tvEggI.text = state.backpack?.eggI.toString()
            tvEggII.text = state.backpack?.eggII.toString()
            tvEggIII.text = state.backpack?.eggIII.toString()
        }

        viewModel.error.observeError()
    }

}
