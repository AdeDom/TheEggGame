package com.adedom.theegggame.ui.multi.room

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.adedom.utility.extension.*
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker

class CreateRoomDialog :
    BaseDialogFragment<RoomActivityViewModel>({ R.layout.dialog_create_room }) {

    private lateinit var mEtName: EditText
    private lateinit var mNumberPicker: ScrollableNumberPicker
    private lateinit var mBtnCreateRoom: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RoomActivityViewModel::class.java)

        init(bView)

        return AlertDialog.Builder(activity!!)
            .dialog(bView, R.drawable.ic_add_black, R.string.create_room)
    }

    private fun init(view: View) {
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
        val playerId = GameActivity.sContext.getPrefLogin(PLAYER_ID)
        val date = getDateTime(DATE)
        val time = getDateTime(TIME)
        viewModel.createRoom(name, people, playerId, date, time).observe(this, Observer {
            if (it.result == FAILED) {
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