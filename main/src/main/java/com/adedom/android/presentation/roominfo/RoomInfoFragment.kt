package com.adedom.android.presentation.roominfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.android.util.toast
import com.adedom.teg.presentation.roominfo.RoomInfoViewModel
import kotlinx.android.synthetic.main.fragment_room_info.*
import kotlinx.android.synthetic.main.item_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomInfoFragment : BaseFragment(R.layout.fragment_room_info) {

    private val viewModel by viewModel<RoomInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.incomingRoomInfoTitle()
            viewModel.incomingRoomInfoPlayers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            state.roomInfoTitle?.let {
                tvRoomNo.text = getString(R.string.room_title_room_no, it.roomNo)
                tvRoomName.text = getString(R.string.room_title_room_name, it.name)
                tvRoomPeople.text = getString(R.string.room_title_room_people, it.people)
            }

            if (state.roomInfoPlayers.isNotEmpty()) {
                context.toast(state.roomInfoPlayers.toString(), Toast.LENGTH_LONG)
            }
        }

        viewModel.error.observeError()

        btGoTeg.setOnClickListener {
            viewModel.callMultiItemCollection()
        }
    }

}
