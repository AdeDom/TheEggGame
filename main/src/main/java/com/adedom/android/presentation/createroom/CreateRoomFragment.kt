package com.adedom.android.presentation.createroom

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.clicks
import com.adedom.android.util.hideSoftKeyboard
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.createroom.CreateRoomViewEvent
import com.adedom.teg.presentation.createroom.CreateRoomViewModel
import kotlinx.android.synthetic.main.fragment_create_room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class CreateRoomFragment : BaseFragment(R.layout.fragment_create_room) {

    private val viewModel by viewModel<CreateRoomViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            tvRoomPeople.text = state.roomPeople.toString()

            btCreateRoom.isClickable = state.isClickable
        }

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            etRoomName.setText(playerInfo.name)
        })

        viewModel.createRoomEvent.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_createRoomFragment_to_roomInfoFragment)
            } else {
                rootLayout.snackbar(response.message)
            }
        }

        viewModel.error.observeError()

        etRoomName.addTextChangedListener { viewModel.setStateRoomName(it.toString()) }

        btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btCreateRoom.setOnClickListener {
            viewModel.callCreateRoom()
        }

        viewEvent().observe { viewModel.process(it) }

        rootLayout.setOnClickListener { activity?.hideSoftKeyboard() }
    }

    private fun viewEvent(): Flow<CreateRoomViewEvent> {
        return merge(
            ivArrowLeft.clicks().map { CreateRoomViewEvent.ArrowLeft },
            ivArrowRight.clicks().map { CreateRoomViewEvent.ArrowRight },
        )
    }

}
