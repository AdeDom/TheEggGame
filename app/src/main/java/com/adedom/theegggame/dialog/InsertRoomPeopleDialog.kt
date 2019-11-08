package com.adedom.theegggame.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.model.RoomItem
import com.adedom.theegggame.multi.GetReadyActivity
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.utility.MyCode
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyIntent
import com.adedom.theegggame.utility.MyResultSet
import com.adedom.utility.MyLibrary
import java.sql.ResultSet

class InsertRoomPeopleDialog : DialogFragment() { // 21/7/62

    private lateinit var mName: TextInputLayout
    private lateinit var mPeople: LinearLayout
    private lateinit var mEdtPassword: EditText
    private lateinit var mBtnCreateRoom: Button
    private lateinit var mRoomItem: RoomItem

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.item_room, null)

        mRoomItem = arguments!!.getParcelable<Parcelable>("room") as RoomItem

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setTitle("Join Room")

        bindWidgets(view)
        setWidgets()
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mName = view.findViewById(R.id.name) as TextInputLayout
        mPeople = view.findViewById(R.id.people) as LinearLayout
        mEdtPassword = view.findViewById(R.id.mEdtPassword) as EditText
        mBtnCreateRoom = view.findViewById(R.id.mBtnCreateRoom) as Button
    }

    private fun setWidgets() {
        mName.visibility = View.GONE
        mPeople.visibility = View.GONE
        mBtnCreateRoom.text = "Join Room"
    }

    private fun setEvents() {
        mBtnCreateRoom.setOnClickListener { checkRoomToJoin() }
    }

    private fun checkRoomToJoin() {
        if (MyLibrary.isEmpty(mEdtPassword, getString(R.string.error_password))) return

        val password = mEdtPassword.text.toString().trim()
        if (password == mRoomItem.password) {
            checkPeopleInRoom()
        } else {
            MyLibrary.with(RoomActivity.context).showLong(R.string.password_incorrect)
        }
    }

    private fun checkPeopleInRoom() {
        val sql = "SELECT COUNT(*) FROM tbl_room_info WHERE room_no = '${mRoomItem.no}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                if (rs.next()) {
                    if (rs.getInt(1) < mRoomItem.people.toInt()) {
                        insertPeople()
                    } else {
                        dialog.dismiss()
                        MyLibrary.with(RoomActivity.context).showLong(R.string.full)
                    }
                }
            }
        })
    }

    private fun insertPeople() {
        val sql = "INSERT INTO tbl_room_info (room_no, player_id, team, status_id) \n" +
                "VALUES ('${mRoomItem.no.trim()}', " +
                "'${MainActivity.mPlayerItem.playerId.trim()}', " +
                "'${MyCode.rndTeam().trim()}', " +
                "'0')"
        MyConnect.executeQuery(sql)

        dialog.dismiss()
        MyIntent().getIntent(
            context!!,
            GetReadyActivity::class.java,
            mRoomItem.no,
            mRoomItem.name,
            mRoomItem.people,
            "2"
        )
    }
}
