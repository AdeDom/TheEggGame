package com.adedom.theegggame.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.multi.GetReadyActivity
import com.adedom.theegggame.utility.MyCode
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyIntent
import com.adedom.theegggame.utility.MyResultSet
import com.adedom.utility.MyLibrary
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker
import java.sql.ResultSet

class InsertRoomDialog : DialogFragment() { // 21/7/62

    private lateinit var mEdtName: EditText
    private lateinit var mEdtPassword: EditText
    private lateinit var mNumberPicker: ScrollableNumberPicker
    private lateinit var mBtnCreateRoom: Button
    private var mNoRoom = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.item_room, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_add_black)
            .setTitle("Create Room")

        bindWidgets(view)
        setWidgets()
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mEdtName = view.findViewById(R.id.mEdtName) as EditText
        mEdtPassword = view.findViewById(R.id.mEdtPassword) as EditText
        mNumberPicker = view.findViewById(R.id.mNumberPicker) as ScrollableNumberPicker
        mBtnCreateRoom = view.findViewById(R.id.mBtnCreateRoom) as Button
    }

    private fun setWidgets() {
        mEdtName.setText(MainActivity.mPlayerItem.name)
    }

    private fun setEvents() {
        mBtnCreateRoom.setOnClickListener { getNoRoomToInsert() }
    }

    private fun getNoRoomToInsert() {
        if (MyLibrary.isEmpty(mEdtName, getString(R.string.error_room_name))) return

        val sql = "SELECT no FROM tbl_room ORDER BY id DESC LIMIT 1"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                mNoRoom = if (rs.next()) {
                    (rs.getInt(1) + 1).toString()
                } else {
                    "1"
                }

                insertRoom()
            }
        })
    }

    private fun insertRoom() {
        val sqlRoom = "INSERT INTO tbl_room (no, name, password, people, status_id) \n" +
                "VALUES ('${mNoRoom.trim()}', " +
                "'${mEdtName.text.toString().trim()}', " +
                "'${mEdtPassword.text.toString().trim()}', " +
                "'${mNumberPicker.value.toString().trim()}', " +
                "'1')"
        MyConnect.executeQuery(sqlRoom)

        val sqlRoomInfo = "INSERT INTO tbl_room_info (room_no, player_id, team, status_id) \n" +
                "VALUES ('${mNoRoom.trim()}', " +
                "'${MainActivity.mPlayerItem.playerId.trim()}', " +
                "'${MyCode.rndTeam().trim()}', " +
                "'0')"
        MyConnect.executeQuery(sqlRoomInfo)

        dialog.dismiss()
        MyIntent().getIntent(
            context!!,
            GetReadyActivity::class.java,
            mNoRoom,
            mEdtName.text,
            mNumberPicker.value,
            "1"
        )
    }
}
