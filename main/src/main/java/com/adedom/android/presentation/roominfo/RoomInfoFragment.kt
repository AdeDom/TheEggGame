package com.adedom.android.presentation.roominfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.ItemDecoration
import com.adedom.android.util.clicks
import com.adedom.android.util.setVisibility
import com.adedom.android.util.toast
import com.adedom.teg.models.websocket.RoomInfoTegMultiOutgoing
import com.adedom.teg.presentation.roominfo.RoomInfoTegMultiListener
import com.adedom.teg.presentation.roominfo.RoomInfoViewEvent
import com.adedom.teg.presentation.roominfo.RoomInfoViewModel
import kotlinx.android.synthetic.main.fragment_room_info.*
import kotlinx.android.synthetic.main.item_room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class RoomInfoFragment : BaseFragment(R.layout.fragment_room_info), RoomInfoTegMultiListener {

    private val viewModel by viewModel<RoomInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.incomingRoomInfoTitle()
            viewModel.incomingRoomInfoPlayers()
            viewModel.incomingRoomInfoTegMulti()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adt = RoomInfoAdapter()
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adt
            addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(10, resources), true))
        }

        viewModel.listener = this

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            state.roomInfoTitle?.let {
                tvRoomNo.text = getString(R.string.room_title_room_no, it.roomNo)
                tvRoomName.text = getString(R.string.room_title_room_name, it.name)
                tvRoomPeople.text = getString(R.string.room_title_room_people, it.people)
            }

            if (state.roomInfoPlayers.isNotEmpty()) {
                adt.setList(state.roomInfoPlayers)
            }

            if (state.isRoleHead) {
                btGoTeg.setText(R.string.go)
            }
        }

        viewModel.leaveRoomInfoEvent.observe { response ->
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        viewModel.goTegMultiEvent.observe { response ->
            if (!response.success) {
                context.toast(response.message, Toast.LENGTH_LONG)
            }
        }

        viewModel.error.observeError()

        viewEventFlow().observe { viewModel.process(it) }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle(R.string.dialog_room_info_title)
                        setMessage(R.string.dialog_room_info_message)
                        setPositiveButton(android.R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setNegativeButton(android.R.string.ok) { _, _ ->
                            viewModel.callLeaveRoomInfo()
                        }
                        show()
                    }
                }
            })
    }

    private fun viewEventFlow(): Flow<RoomInfoViewEvent> {
        return merge(
            btGoTeg.clicks().map { RoomInfoViewEvent.GoTeg },
            ivTeamA.clicks().map { RoomInfoViewEvent.TeamA },
            ivTeamB.clicks().map { RoomInfoViewEvent.TeamB },
        )
    }

    override fun roomInfoTegMultiResponse(roomInfoTegMultiOutgoing: RoomInfoTegMultiOutgoing) {
        if (roomInfoTegMultiOutgoing.success) {
            findNavController().navigate(R.id.action_roomInfoFragment_to_multiFragment)
        }
    }

    override fun onPause() {
        viewModel.callChangeStatusUnready()
        super.onPause()
    }

}
