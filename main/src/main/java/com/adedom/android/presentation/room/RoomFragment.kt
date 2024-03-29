package com.adedom.android.presentation.room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.ItemDecoration
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.room.JoinRoomInfoListener
import com.adedom.teg.presentation.room.RoomViewModel
import kotlinx.android.synthetic.main.fragment_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomFragment : BaseFragment(R.layout.fragment_room), JoinRoomInfoListener {

    private val viewModel by viewModel<RoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callChangeCurrentModeMulti()
            viewModel.incomingRoomPeopleAll()
            viewModel.incomingPlaygroundRoom()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adt = RoomAdapter()
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adt
            addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(10, resources), true))
        }

        viewModel.listener = this

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            adt.setList(state.rooms)

            tvPeopleAll.text = state.peopleAll.toString()

            if (state.isClickable) {
                adt.onClick = { viewModel.callJoinRoomInfo(it.roomNo) }
            } else {
                adt.onClick = null
            }
        }

        viewModel.error.observeError()

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_roomFragment_to_createRoomFragment)
        }
    }

    override fun onJoinRoomInfoResponse(response: BaseResponse) {
        if (response.success) {
            findNavController().navigate(R.id.action_roomFragment_to_roomInfoFragment)
        } else {
            requireView().snackbar(response.message)
        }
    }

}
