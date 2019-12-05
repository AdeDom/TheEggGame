package com.adedom.theegggame.ui.dialogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.getready.GetReadyActivity
import com.adedom.theegggame.util.BaseActivity
import com.adedom.theegggame.util.MyCode
import com.adedom.theegggame.util.MyConnect
import com.adedom.theegggame.util.MyResultSet
import com.adedom.utility.isEmpty
import com.adedom.utility.toast
import com.google.android.material.textfield.TextInputLayout
import java.sql.ResultSet

class InsertRoomPeopleDialog : DialogFragment() { // 21/7/62

    private lateinit var mName: TextInputLayout
    private lateinit var mPeople: LinearLayout
    private lateinit var mEdtPassword: EditText
    private lateinit var mBtnCreateRoom: Button
    private lateinit var mRoomItem: Room

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.item_room, null)

        mRoomItem = arguments!!.getParcelable<Parcelable>("room") as Room

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
        if (mEdtPassword.isEmpty(getString(R.string.error_password))) return

//        val password = mEdtPassword.text.toString().trim()
//        if (password == mRoomItem.password) {
//            checkPeopleInRoom()
//        } else {
//            GameActivity.sContext.toast(R.string.password_incorrect, Toast.LENGTH_LONG)
//        }
    }

    private fun checkPeopleInRoom() {
        val sql = "SELECT COUNT(*) FROM tbl_room_info WHERE room_no = '${mRoomItem.room_no}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                if (rs.next()) {
                    if (rs.getInt(1) < mRoomItem.people!!.toInt()) {
                        insertPeople()
                    } else {
                        dialog!!.dismiss()
                        BaseActivity.sContext.toast(R.string.full, Toast.LENGTH_LONG)
                    }
                }
            }
        })
    }

    private fun insertPeople() {
        val sql = "INSERT INTO tbl_room_info (room_no, player_id, team, status_id) \n" +
                "VALUES ('${mRoomItem.room_no!!.trim()}', " +
                "'${MainActivity.sPlayerItem.playerId!!.trim()}', " +
                "'${MyCode.rndTeam().trim()}', " +
                "'0')"
        MyConnect.executeQuery(sql)

        dialog!!.dismiss()
        BaseActivity.sContext.startActivity(
            Intent(BaseActivity.sContext, GetReadyActivity::class.java)
                .putExtra("values1", mRoomItem.room_no)
                .putExtra("values2", mRoomItem.name)
                .putExtra("values3", mRoomItem.people)
                .putExtra("values4", "2")
        )
    }
}
