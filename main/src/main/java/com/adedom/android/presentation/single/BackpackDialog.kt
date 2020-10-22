package com.adedom.android.presentation.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adedom.android.R
import com.adedom.android.util.toast
import com.adedom.teg.presentation.single.SingleViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_backpack.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BackpackDialog : BottomSheetDialogFragment() {

    private val viewModel by viewModel<SingleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_backpack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.attachFirstTime.observe(viewLifecycleOwner, {
            viewModel.callFetchItemCollection()
        })

        viewModel.state.observe(viewLifecycleOwner, { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            tvEggI.text = state.backpack?.eggI.toString()
            tvEggII.text = state.backpack?.eggII.toString()
            tvEggIII.text = state.backpack?.eggIII.toString()
        })

        viewModel.error.observe(viewLifecycleOwner, {
            context.toast(it.throwable.message)
        })
    }

}
