package com.adedom.theegggame.ui.multi.room

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

class CreateRoomDialog : BaseDialogFragment<RoomActivityViewModel>(
    { R.layout.dialog_create_room },
    { R.drawable.ic_add_black },
    { R.string.create_room }
) {

    private lateinit var mEtName: EditText
    private lateinit var mNumberPicker: ScrollableNumberPicker
    private lateinit var mBtnCreateRoom: Button

    override fun initDialog(view: View) {
        super.initDialog(view)
        viewModel = ViewModelProviders.of(this).get(RoomActivityViewModel::class.java)

        mEtName = view.findViewById(R.id.mEtName) as EditText
        mNumberPicker = view.findViewById(R.id.mNumberPicker) as ScrollableNumberPicker
        mBtnCreateRoom = view.findViewById(R.id.mBtCreateRoom) as Button

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
