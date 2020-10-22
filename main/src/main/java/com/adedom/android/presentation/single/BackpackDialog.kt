package com.adedom.android.presentation.single

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseDialogFragment
import com.adedom.teg.presentation.single.SingleViewModel
import kotlinx.android.synthetic.main.dialog_backpack.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BackpackDialog : BaseDialogFragment(R.layout.dialog_backpack) {

    private val viewModel by viewModel<SingleViewModel>()

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

        btHide.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
