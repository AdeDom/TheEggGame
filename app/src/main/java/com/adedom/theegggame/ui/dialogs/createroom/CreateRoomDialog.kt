package com.adedom.theegggame.ui.dialogs.createroom

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker

class CreateRoomDialog : DialogFragment() { //5/12/19

    private lateinit var mViewModel: CreateRoomDialogViewModel
    private lateinit var mEdtName: EditText
    private lateinit var mNumberPicker: ScrollableNumberPicker
    private lateinit var mBtnCreateRoom: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val factory = CreateRoomDialogFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(CreateRoomDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_create_room, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_add_black)
            .setTitle(R.string.create_room)

        init(view)

        return builder.create()
    }

    private fun init(view: View) {
        mEdtName = view.findViewById(R.id.mEdtName) as EditText
        mNumberPicker = view.findViewById(R.id.mNumberPicker) as ScrollableNumberPicker
        mBtnCreateRoom = view.findViewById(R.id.mBtnCreateRoom) as Button

        mEdtName.setText(MainActivity.sPlayerItem.name)

        mBtnCreateRoom.setOnClickListener { createRoom() }
    }

    private fun createRoom() {
        if (mEdtName.isEmpty(getString(R.string.error_room_name))) return

        val name = mEdtName.text.toString().trim()
        val people = mNumberPicker.value.toString().trim()
        val playerId = MainActivity.sPlayerItem.playerId
        mViewModel.insertRoom(name, people, playerId!!).observe(this, Observer {
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
