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
import com.adedom.android.util.clicks
import com.adedom.android.util.setVisibility
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
class RoomInfoFragment : BaseFragment(R.layout.fragment_room_info) {

    private val viewModel by viewModel<RoomInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.attachFirstTime.observe {
            viewModel.callCurrentRoomNo()
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
                adt.setList(state.roomInfoPlayers)
            }

            if (state.isRoleHead) {
                btGoTeg.setText(R.string.go)
            }
        }

        viewModel.currentRoomNo.observe { response ->
            if (response.success) {
                viewModel.incomingRoomInfoTitle(response.roomNo)
                viewModel.incomingRoomInfoPlayers(response.roomNo)
            }
        }

        viewModel.leaveRoomInfoEvent.observe { response ->
            if (response.success) {
                findNavController().popBackStack()
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

}
