package com.adedom.android.presentation.room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.ItemDecoration
import com.adedom.android.util.setVisibility
import com.adedom.android.util.toast
import com.adedom.teg.presentation.room.RoomViewModel
import kotlinx.android.synthetic.main.fragment_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomFragment : BaseFragment(R.layout.fragment_room) {

    private val viewModel by viewModel<RoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.incomingRoomPeopleAll()
            viewModel.incomingPlaygroundRoom()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adt = RoomAdapter(context)
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adt
            addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(10, resources), true))
        }

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            adt.setList(state.rooms)

            tvPeopleAll.text = state.peopleAll.toString()
        }

        viewModel.joinRoomInfo.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_roomFragment_to_roomInfoFragment)
            } else {
                context.toast(response.message)
            }
        }

        viewModel.error.observeError()

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_roomFragment_to_createRoomFragment)
        }

        adt.onClick = {
            viewModel.callJoinRoomInfo(it.roomNo)
        }
    }

}
