package com.adedom.android.presentation.room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_room.*

class RoomFragment : BaseFragment(R.layout.fragment_room) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btRoomInfo.setOnClickListener {
            findNavController().navigate(R.id.action_roomFragment_to_roomInfoFragment)
        }

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_roomFragment_to_createRoomFragment)
        }
    }

}
