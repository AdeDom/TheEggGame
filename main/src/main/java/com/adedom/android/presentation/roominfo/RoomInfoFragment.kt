package com.adedom.android.presentation.roominfo

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.roominfo.RoomInfoViewModel
import kotlinx.android.synthetic.main.fragment_room_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomInfoFragment : BaseFragment(R.layout.fragment_room_info) {

    private val viewModel by viewModel<RoomInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)
        }

        viewModel.error.observeError()

        btGoTeg.setOnClickListener {
            viewModel.callMultiItemCollection()
        }
    }

}
