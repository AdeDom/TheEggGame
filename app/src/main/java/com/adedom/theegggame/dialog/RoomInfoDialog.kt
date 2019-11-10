package com.adedom.theegggame.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.model.RoomInfoItem
import com.adedom.theegggame.utility.MyConnect

class RoomInfoDialog : DialogFragment() { // 21/7/62

    private lateinit var mRoomInfoItem: RoomInfoItem
    private lateinit var mBtnKickOut: Button
    private lateinit var mContext: Context

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_room_info, null)

        mContext = context!!
        mRoomInfoItem = arguments!!.getParcelable<Parcelable>("data") as RoomInfoItem

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)

        bindWidgets(view)
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mBtnKickOut = view.findViewById(R.id.mBtnKickOut) as Button
    }

    private fun setEvents() {
        mBtnKickOut.setOnClickListener { onKickOut() }
    }

    private fun onKickOut() {
        dialog!!.dismiss()
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Do you want to kick out ${mRoomInfoItem.name} ?")
            .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
            .setNegativeButton(R.string.yes) { dialog, which ->
                dialog.dismiss()
                deletePlayerRoomInfo(mRoomInfoItem.playerId)
            }.show()
    }

    private fun deletePlayerRoomInfo(playerId: String) {
        val sql = "DELETE FROM tbl_room_info \n" +
                "WHERE player_id = ${playerId.trim()}"
        MyConnect.executeQuery(sql)
    }
}
