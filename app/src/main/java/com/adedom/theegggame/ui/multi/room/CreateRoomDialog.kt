package com.adedom.theegggame.ui.multi.room

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.failed
import com.adedom.library.extension.getContent
import com.adedom.library.extension.isEmpty
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.HEAD
import com.adedom.theegggame.util.KEY_FAILED
import com.adedom.theegggame.util.ROOM
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker

class CreateRoomDialog :
    BaseDialogFragment<RoomActivityViewModel>({ R.layout.dialog_create_room }) {

    private lateinit var mEtName: EditText
    private lateinit var mNumberPicker: ScrollableNumberPicker
    private lateinit var mBtnCreateRoom: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RoomActivityViewModel::class.java)

        init()

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_add_black, R.string.create_room)
    }

    private fun init() {
        mEtName = v.findViewById(R.id.mEtName) as EditText
        mNumberPicker = v.findViewById(R.id.mNumberPicker) as ScrollableNumberPicker
        mBtnCreateRoom = v.findViewById(R.id.mBtCreateRoom) as Button

        mEtName.setText(MainActivity.sPlayer.name)

        mBtnCreateRoom.setOnClickListener { createRoom() }
    }

    private fun createRoom() {
        if (mEtName.isEmpty(getString(R.string.error_room_name))) return

        val name = mEtName.getContent()
        val people = mNumberPicker.value.toString().trim()
        val playerId = MainActivity.sPlayer.playerId
        viewModel.createRoom(name, people, playerId).observe(this, Observer {
            if (it.result == KEY_FAILED) {
                GameActivity.sContext.failed()
            } else {
                dialog!!.dismiss()
                startActivity(
                    Intent(GameActivity.sContext, RoomInfoActivity::class.java)
                        .putExtra(ROOM, Room(null, it.result, name, people, HEAD))
                )
            }
        })
    }
}
