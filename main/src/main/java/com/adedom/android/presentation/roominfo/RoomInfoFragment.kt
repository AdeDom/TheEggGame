package com.adedom.android.presentation.roominfo

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.ItemDecoration
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.roominfo.RoomInfoViewModel
import com.adedom.teg.util.TegConstant
import kotlinx.android.synthetic.main.fragment_room_info.*
import kotlinx.android.synthetic.main.item_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomInfoFragment : BaseFragment(R.layout.fragment_room_info) {

    private val viewModel by viewModel<RoomInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callCurrentRoomNo()
            viewModel.incomingRoomInfoPlayers()
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

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            state.roomInfoTitle?.let {
                tvRoomNo.text = getString(R.string.room_title_room_no, it.roomNo)
                tvRoomName.text = getString(R.string.room_title_room_name, it.name)
                tvRoomPeople.text = getString(R.string.room_title_room_people, it.people)
            }

            if (state.roomInfoPlayers.isNotEmpty()) {
                state.roomInfoPlayers
                    .filter { it.playerId == state.playerId }
                    .onEach {
                        if (it.roleRoomInfo == TegConstant.ROOM_ROLE_HEAD) {
                            btGoTeg.setText(R.string.go)
                        }
                    }

                adt.setList(state.roomInfoPlayers)
            }
        }

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            viewModel.setStatePlayerId(playerInfo.playerId)
        })

        viewModel.currentRoomNo.observe { response ->
            if (response.success) {
                viewModel.setStateRoomNo(response.roomNo)
                viewModel.incomingRoomInfoTitle()
            }
        }

        viewModel.leaveRoomInfoEvent.observe { response ->
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        viewModel.error.observeError()

        btGoTeg.setOnClickListener {
            viewModel.callMultiItemCollection()
        }

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

}
