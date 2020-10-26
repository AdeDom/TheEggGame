package com.adedom.android.presentation.createroom

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_room.*

class CreateRoomFragment : BaseFragment(R.layout.fragment_create_room) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btCreateRoom.setOnClickListener {
            findNavController().navigate(R.id.action_createRoomFragment_to_roomInfoFragment)
        }

    }

}
